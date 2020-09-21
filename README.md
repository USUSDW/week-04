# Week 4: Interfaces and Decoupling

Welcome back to the Fall 2020 Edition of the Software Development Workshop.
Today we're going to talk about a concept that doesn't really come up until
we're working with larger and more complex applications: Interfaces and
Decoupling. These are useful for setting your program up to handle different
functionality, even if you don't know exactly how that's going to work. We do
this by defining *what* pieces should be able to do, and then leaving the
implementation for later. This is exactly what interfaces do.

## Interfaces
Interfaces are Java's way of defining functionality without defining how it 
should be run. These can be simple, like a `Runnable`:

```java
public class HelloPrinter implements Runnable {
    @Override
    public void run() {
        System.out.println("Hello!");
    }
}
```

or can be more complex like an 
[`Iterator`](./src/main/java/com/github/ususdw/examples/LinkedList.java):

```java
public class LinkedListIterator<T> implements Iterator<T> {
    private LinkedNode<T> current;

    public LinkedListIterator(LinkedList<T> list) {
        this.list = list.head;
    }

    @Override
    public boolean hasNext() {
        return current == null;
    }

    @Override
    public T next() {
        var tmp = current.data;
        current = current.next;
        return tmp;
    }
}
```

Notice how we're overriding things we get from each interface. We get the 
templates for each of the functions from the interface, then write the method 
body wherever we're implementing them. For example, the interface for `Runnable`
looks like this:

```java
public interface Runnable {
    void run();
}
```

That's it. We used interfaces in Week 2 with the Adapter pattern, but now it's
time that we write our own. In this application, we're going to work on 
getting data from a couple of sources and converting it into information our
application can use. This includes from json on the web, a yaml file in this
project, and in code that we can call from inside of the application.

## Loose Coupling

Couplers are the pieces on train cars that hold them together. The neat thing
about train couplings are that they're loose enough to let you switch cars out
as often as you want, but tight enough to keep them together while in transit.

![Couplers on a train](https://upload.wikimedia.org/wikipedia/commons/thumb/6/6b/Carrage_couplings_on_ABT_Railway_%283939380014%29.jpg/340px-Carrage_couplings_on_ABT_Railway_%283939380014%29.jpg)

*Photo by Tom Worthington on Wikimedia. [(Source)](https://en.wikipedia.org/wiki/File:Carrage_couplings_on_ABT_Railway_(3939380014).jpg)*

Well, right now our code has a bunch of different places it has to reach out to
get data. Take for example the CommandHandler constructor. It has to
specifically reach out to both the Local and Remote JSON stores by name. If we
want to add a different type, say a local YAML storage or a remote API storage,
we would have to adjust multiple files in multiple places. And what about the
Article class? Notice how it specifically takes a LocalJsonAuthorStore? If we
add another way to store authors, we have to add another constructor to each of
them, and change how the function to find its author works. 

What we've done here is locked the train couplers together, welded them
together, wrapped them in steel chains, then welded the chains to the coupler.
In order to get thse two cars apart, we're going to need to get out bolt
cutters, maybe even a full pneumatic cutter. If this train stops abruptly, these
cars aren't going to look pretty.

That's not good.

So what can we do to fix that? This is where decoupling these things can help.
Before you read past this point, think about how you would solve that. We'll 
discuss this in the club meeting, but if you're reading this you're probably
not there.

Let's get out those bolt cutters.

## The Solution

What I propose is that we use two interfaces. One for MutableStores (LocalJson),
and one for ImmutableStores (RemoteJsonArticleStore). MutableStores will 
provide two methods: `add`, and `getAll`, where ImmutableStores only get one
method, `add`. If you're familiar with inheritance, there's also another
trick we can do to cut the interfaces down even further: we can have 
MutableStore extend ImmutableStore, allowing MutableStores to be used as 
ImmutableStores. Here's what I propose:

`ImmutableStore.java`
```java
public interface ImmutableStore<T> {
    List<T> getAll();
}
```

`MutableStore.java`
```java
public interface MutableStore<T> extends ImmutableStore<T> {
    add(T item);
}
```

This is exactly what we're going for. We can pretty easily adjust each of the
stores to `implement` their paired interface, then only talk about the 
interface where we use it in code. 

`LocalJsonArticleStore.java`
```java
public class LocalJsonArticleStore implements MutableStore<Article> {

    private final File file;
    private final Type listType = new TypeToken<ArrayList<Article>>(){}.getType();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final LocalJsonAuthorStore authorStore;

    public LocalJsonArticleStore(String path,
        ImmutableStore<Author> authorStore) {
        file = new File(path);
        this.authorStore = authorStore;
    }

    public List<Article> getAll() {
        if (!file.exists()) {
            return List.of();
        }
        try {
            var reader = new FileReader(file);
            var text = FileUtils.readAll(reader);
            reader.close();
            List<Article> articles = gson.fromJson(text, listType);
            for (var article : articles) {
                article.setAuthorStore(authorStore);
            }
            return articles;
        } catch (IOException e) {
            return List.of();
        }
    }

    public void add(Article article) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        else {
            var articles = getAll();
            articles.add(article);
            String text = gson.toJson(articles);
            var writer = new FileWriter(file, StandardCharsets.UTF_8, false);
            writer.write(text);
            writer.close();
        }
    }
}
```
In Article we can now take an `ImmutableStore<Author>` instead of a
LocalJsonAuthorStore and have the flexibility to add a RemoteJsonAuthorStore
instead. There's already one in the project, so let's go ahead and make that
adjustment, and give it this URL:

```text
https://gist.githubusercontent.com/hhenrichsen/c63287e1780258e270c13e806c4608b5/raw/3e0290937dcf6aaa178a2bf3fee1685506921579/authors.json
```

The benefits are really impressive for this 'small' change. We now can easily
swap out which implementation we want, and even add new ones without causing
our program to need to be adjusted everywhere. We can continue with this 
refactor much further than these two changes, such as making CommandHandler 
take lists of stores for articles and authors, regardless of where they come
from or where they store things. 

I've done some of these refactors on the `refactored` branch. If you cloned
the project, you can run `git checkout refactored` to explore the project at
this point.

## What's Next
There's one last quirk about Interfaces that make them even more powerful. They
give us the ability to use lambdas and function references to implement them,
and allow for us to implement them inline. 

This is what we're going to talk about in week 5! See you then!

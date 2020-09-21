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

So what's this loose coupling thing, then? Well, right now our code has a bunch
of different places it has to reach out to to get data. Take for example the

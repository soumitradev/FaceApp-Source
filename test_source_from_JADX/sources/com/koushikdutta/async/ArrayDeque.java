package com.koushikdutta.async;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayDeque<E> extends AbstractCollection<E> implements Deque<E>, Cloneable, Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int MIN_INITIAL_CAPACITY = 8;
    private static final long serialVersionUID = 2340985798034038923L;
    private transient Object[] elements;
    private transient int head;
    private transient int tail;

    private class DeqIterator implements Iterator<E> {
        private int cursor;
        private int fence;
        private int lastRet;

        private DeqIterator() {
            this.cursor = ArrayDeque.this.head;
            this.fence = ArrayDeque.this.tail;
            this.lastRet = -1;
        }

        public boolean hasNext() {
            return this.cursor != this.fence;
        }

        public E next() {
            if (this.cursor == this.fence) {
                throw new NoSuchElementException();
            }
            E result = ArrayDeque.this.elements[this.cursor];
            if (ArrayDeque.this.tail == this.fence) {
                if (result != null) {
                    this.lastRet = this.cursor;
                    this.cursor = (this.cursor + 1) & (ArrayDeque.this.elements.length - 1);
                    return result;
                }
            }
            throw new ConcurrentModificationException();
        }

        public void remove() {
            if (this.lastRet < 0) {
                throw new IllegalStateException();
            }
            if (ArrayDeque.this.delete(this.lastRet)) {
                this.cursor = (this.cursor - 1) & (ArrayDeque.this.elements.length - 1);
                this.fence = ArrayDeque.this.tail;
            }
            this.lastRet = -1;
        }
    }

    private class DescendingIterator implements Iterator<E> {
        private int cursor;
        private int fence;
        private int lastRet;

        private DescendingIterator() {
            this.cursor = ArrayDeque.this.tail;
            this.fence = ArrayDeque.this.head;
            this.lastRet = -1;
        }

        public boolean hasNext() {
            return this.cursor != this.fence;
        }

        public E next() {
            if (this.cursor == this.fence) {
                throw new NoSuchElementException();
            }
            this.cursor = (this.cursor - 1) & (ArrayDeque.this.elements.length - 1);
            E result = ArrayDeque.this.elements[this.cursor];
            if (ArrayDeque.this.head == this.fence) {
                if (result != null) {
                    this.lastRet = this.cursor;
                    return result;
                }
            }
            throw new ConcurrentModificationException();
        }

        public void remove() {
            if (this.lastRet < 0) {
                throw new IllegalStateException();
            }
            if (!ArrayDeque.this.delete(this.lastRet)) {
                this.cursor = (this.cursor + 1) & (ArrayDeque.this.elements.length - 1);
                this.fence = ArrayDeque.this.head;
            }
            this.lastRet = -1;
        }
    }

    private void allocateElements(int numElements) {
        int initialCapacity = 8;
        if (numElements >= 8) {
            initialCapacity = numElements;
            initialCapacity |= initialCapacity >>> 1;
            initialCapacity |= initialCapacity >>> 2;
            initialCapacity |= initialCapacity >>> 4;
            initialCapacity |= initialCapacity >>> 8;
            initialCapacity = (initialCapacity | (initialCapacity >>> 16)) + 1;
            if (initialCapacity < 0) {
                initialCapacity >>>= 1;
            }
        }
        this.elements = new Object[initialCapacity];
    }

    private void doubleCapacity() {
        int p = this.head;
        int n = this.elements.length;
        int r = n - p;
        int newCapacity = n << 1;
        if (newCapacity < 0) {
            throw new IllegalStateException("Sorry, deque too big");
        }
        Object[] a = new Object[newCapacity];
        System.arraycopy(this.elements, p, a, 0, r);
        System.arraycopy(this.elements, 0, a, r, p);
        this.elements = a;
        this.head = 0;
        this.tail = n;
    }

    private <T> T[] copyElements(T[] a) {
        if (this.head < this.tail) {
            System.arraycopy(this.elements, this.head, a, 0, size());
        } else if (this.head > this.tail) {
            int headPortionLen = this.elements.length - this.head;
            System.arraycopy(this.elements, this.head, a, 0, headPortionLen);
            System.arraycopy(this.elements, 0, a, headPortionLen, this.tail);
        }
        return a;
    }

    public ArrayDeque() {
        this.elements = new Object[16];
    }

    public ArrayDeque(int numElements) {
        allocateElements(numElements);
    }

    public ArrayDeque(Collection<? extends E> c) {
        allocateElements(c.size());
        addAll(c);
    }

    public void addFirst(E e) {
        if (e == null) {
            throw new NullPointerException("e == null");
        }
        Object[] objArr = this.elements;
        int length = (this.head - 1) & (this.elements.length - 1);
        this.head = length;
        objArr[length] = e;
        if (this.head == this.tail) {
            doubleCapacity();
        }
    }

    public void addLast(E e) {
        if (e == null) {
            throw new NullPointerException("e == null");
        }
        this.elements[this.tail] = e;
        int length = (this.tail + 1) & (this.elements.length - 1);
        this.tail = length;
        if (length == this.head) {
            doubleCapacity();
        }
    }

    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }

    public boolean offerLast(E e) {
        addLast(e);
        return true;
    }

    public E removeFirst() {
        E x = pollFirst();
        if (x != null) {
            return x;
        }
        throw new NoSuchElementException();
    }

    public E removeLast() {
        E x = pollLast();
        if (x != null) {
            return x;
        }
        throw new NoSuchElementException();
    }

    public E pollFirst() {
        int h = this.head;
        E result = this.elements[h];
        if (result == null) {
            return null;
        }
        this.elements[h] = null;
        this.head = (h + 1) & (this.elements.length - 1);
        return result;
    }

    public E pollLast() {
        int t = (this.tail - 1) & (this.elements.length - 1);
        E result = this.elements[t];
        if (result == null) {
            return null;
        }
        this.elements[t] = null;
        this.tail = t;
        return result;
    }

    public E getFirst() {
        E result = this.elements[this.head];
        if (result != null) {
            return result;
        }
        throw new NoSuchElementException();
    }

    public E getLast() {
        E result = this.elements[(this.tail - 1) & (this.elements.length - 1)];
        if (result != null) {
            return result;
        }
        throw new NoSuchElementException();
    }

    public E peekFirst() {
        return this.elements[this.head];
    }

    public E peekLast() {
        return this.elements[(this.tail - 1) & (this.elements.length - 1)];
    }

    public boolean removeFirstOccurrence(Object o) {
        if (o == null) {
            return false;
        }
        int mask = this.elements.length - 1;
        int i = this.head;
        while (true) {
            Object obj = this.elements[i];
            Object x = obj;
            if (obj == null) {
                return false;
            }
            if (o.equals(x)) {
                delete(i);
                return true;
            }
            i = (i + 1) & mask;
        }
    }

    public boolean removeLastOccurrence(Object o) {
        if (o == null) {
            return false;
        }
        int mask = this.elements.length - 1;
        int i = (this.tail - 1) & mask;
        while (true) {
            Object obj = this.elements[i];
            Object x = obj;
            if (obj == null) {
                return false;
            }
            if (o.equals(x)) {
                delete(i);
                return true;
            }
            i = (i - 1) & mask;
        }
    }

    public boolean add(E e) {
        addLast(e);
        return true;
    }

    public boolean offer(E e) {
        return offerLast(e);
    }

    public E remove() {
        return removeFirst();
    }

    public E poll() {
        return pollFirst();
    }

    public E element() {
        return getFirst();
    }

    public E peek() {
        return peekFirst();
    }

    public void push(E e) {
        addFirst(e);
    }

    public E pop() {
        return removeFirst();
    }

    private void checkInvariants() {
    }

    private boolean delete(int i) {
        checkInvariants();
        Object[] elements = this.elements;
        int mask = elements.length - 1;
        int h = this.head;
        int t = this.tail;
        int front = (i - h) & mask;
        int back = (t - i) & mask;
        if (front >= ((t - h) & mask)) {
            throw new ConcurrentModificationException();
        } else if (front < back) {
            if (h <= i) {
                System.arraycopy(elements, h, elements, h + 1, front);
            } else {
                System.arraycopy(elements, 0, elements, 1, i);
                elements[0] = elements[mask];
                System.arraycopy(elements, h, elements, h + 1, mask - h);
            }
            elements[h] = null;
            this.head = (h + 1) & mask;
            return false;
        } else {
            if (i < t) {
                System.arraycopy(elements, i + 1, elements, i, back);
                this.tail = t - 1;
            } else {
                System.arraycopy(elements, i + 1, elements, i, mask - i);
                elements[mask] = elements[0];
                System.arraycopy(elements, 1, elements, 0, t);
                this.tail = (t - 1) & mask;
            }
            return true;
        }
    }

    public int size() {
        return (this.tail - this.head) & (this.elements.length - 1);
    }

    public boolean isEmpty() {
        return this.head == this.tail;
    }

    public Iterator<E> iterator() {
        return new DeqIterator();
    }

    public Iterator<E> descendingIterator() {
        return new DescendingIterator();
    }

    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }
        int mask = this.elements.length - 1;
        int i = this.head;
        while (true) {
            Object obj = this.elements[i];
            Object x = obj;
            if (obj == null) {
                return false;
            }
            if (o.equals(x)) {
                return true;
            }
            i = (i + 1) & mask;
        }
    }

    public boolean remove(Object o) {
        return removeFirstOccurrence(o);
    }

    public void clear() {
        int h = this.head;
        int t = this.tail;
        if (h != t) {
            this.tail = 0;
            this.head = 0;
            int i = h;
            int mask = this.elements.length - 1;
            do {
                this.elements[i] = null;
                i = (i + 1) & mask;
            } while (i != t);
        }
    }

    public Object[] toArray() {
        return copyElements(new Object[size()]);
    }

    public <T> T[] toArray(T[] a) {
        int size = size();
        if (a.length < size) {
            a = (Object[]) Array.newInstance(a.getClass().getComponentType(), size);
        }
        copyElements(a);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    public ArrayDeque<E> clone() {
        try {
            ArrayDeque<E> result = (ArrayDeque) super.clone();
            System.arraycopy(this.elements, 0, result.elements, 0, this.elements.length);
            return result;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeInt(size());
        int mask = this.elements.length - 1;
        for (int i = this.head; i != this.tail; i = (i + 1) & mask) {
            s.writeObject(this.elements[i]);
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        int size = s.readInt();
        allocateElements(size);
        int i = 0;
        this.head = 0;
        this.tail = size;
        while (i < size) {
            this.elements[i] = s.readObject();
            i++;
        }
    }
}

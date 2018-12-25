package com.badlogic.gdx.utils;

public class SortedIntList<E> implements Iterable<Node<E>> {
    Node<E> first;
    private Iterator iterator;
    private NodePool<E> nodePool = new NodePool();
    int size = 0;

    class Iterator implements java.util.Iterator<Node<E>> {
        private Node<E> position;
        private Node<E> previousPosition;

        Iterator() {
        }

        public boolean hasNext() {
            return this.position != null;
        }

        public Node<E> next() {
            this.previousPosition = this.position;
            this.position = this.position.f94n;
            return this.previousPosition;
        }

        public void remove() {
            if (this.previousPosition != null) {
                if (this.previousPosition == SortedIntList.this.first) {
                    SortedIntList.this.first = this.position;
                } else {
                    this.previousPosition.f95p.f94n = this.position;
                    if (this.position != null) {
                        this.position.f95p = this.previousPosition.f95p;
                    }
                }
                SortedIntList sortedIntList = SortedIntList.this;
                sortedIntList.size--;
            }
        }

        public Iterator reset() {
            this.position = SortedIntList.this.first;
            this.previousPosition = null;
            return this;
        }
    }

    public static class Node<E> {
        public int index;
        /* renamed from: n */
        protected Node<E> f94n;
        /* renamed from: p */
        protected Node<E> f95p;
        public E value;
    }

    static class NodePool<E> extends Pool<Node<E>> {
        NodePool() {
        }

        protected Node<E> newObject() {
            return new Node();
        }

        public Node<E> obtain(Node<E> p, Node<E> n, E value, int index) {
            Node<E> newNode = (Node) super.obtain();
            newNode.f95p = p;
            newNode.f94n = n;
            newNode.value = value;
            newNode.index = index;
            return newNode;
        }
    }

    public E insert(int index, E value) {
        if (this.first != null) {
            Node<E> c = this.first;
            while (c.f94n != null && c.f94n.index <= index) {
                c = c.f94n;
            }
            if (index > c.index) {
                c.f94n = this.nodePool.obtain(c, c.f94n, value, index);
                if (c.f94n.f94n != null) {
                    c.f94n.f94n.f95p = c.f94n;
                }
                this.size++;
            } else if (index < c.index) {
                Node<E> newFirst = this.nodePool.obtain(null, this.first, value, index);
                this.first.f95p = newFirst;
                this.first = newFirst;
                this.size++;
            } else {
                c.value = value;
            }
        } else {
            this.first = this.nodePool.obtain(null, null, value, index);
            this.size++;
        }
        return null;
    }

    public E get(int index) {
        if (this.first == null) {
            return null;
        }
        Node<E> c = this.first;
        while (c.f94n != null && c.index < index) {
            c = c.f94n;
        }
        if (c.index == index) {
            return c.value;
        }
        return null;
    }

    public void clear() {
        while (this.first != null) {
            this.nodePool.free(this.first);
            this.first = this.first.f94n;
        }
        this.size = 0;
    }

    public int size() {
        return this.size;
    }

    public java.util.Iterator<Node<E>> iterator() {
        if (this.iterator == null) {
            this.iterator = new Iterator();
        }
        return this.iterator.reset();
    }
}

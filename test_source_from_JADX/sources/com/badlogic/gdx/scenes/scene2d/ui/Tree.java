package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.Layout;
import com.badlogic.gdx.scenes.scene2d.utils.Selection;
import com.badlogic.gdx.scenes.scene2d.utils.UIUtils;
import com.badlogic.gdx.utils.Array;

public class Tree extends WidgetGroup {
    private ClickListener clickListener;
    private Node foundNode;
    float iconSpacingLeft;
    float iconSpacingRight;
    float indentSpacing;
    private float leftColumnWidth;
    Node overNode;
    float padding;
    private float prefHeight;
    private float prefWidth;
    final Array<Node> rootNodes;
    final Selection<Node> selection;
    private boolean sizeInvalid;
    TreeStyle style;
    float ySpacing;

    public static class Node {
        Actor actor;
        final Array<Node> children = new Array(0);
        boolean expanded;
        float height;
        Drawable icon;
        Object object;
        Node parent;
        boolean selectable = true;

        public Node(Actor actor) {
            if (actor == null) {
                throw new IllegalArgumentException("actor cannot be null.");
            }
            this.actor = actor;
        }

        public void setExpanded(boolean expanded) {
            if (expanded != this.expanded) {
                this.expanded = expanded;
                if (this.children.size != 0) {
                    Tree tree = getTree();
                    if (tree != null) {
                        int n;
                        int i;
                        if (expanded) {
                            n = this.children.size;
                            for (i = 0; i < n; i++) {
                                ((Node) this.children.get(i)).addToTree(tree);
                            }
                        } else {
                            n = this.children.size;
                            for (i = 0; i < n; i++) {
                                ((Node) this.children.get(i)).removeFromTree(tree);
                            }
                        }
                        tree.invalidateHierarchy();
                    }
                }
            }
        }

        protected void addToTree(Tree tree) {
            tree.addActor(this.actor);
            if (this.expanded) {
                int n = this.children.size;
                for (int i = 0; i < n; i++) {
                    ((Node) this.children.get(i)).addToTree(tree);
                }
            }
        }

        protected void removeFromTree(Tree tree) {
            tree.removeActor(this.actor);
            if (this.expanded) {
                int n = this.children.size;
                for (int i = 0; i < n; i++) {
                    ((Node) this.children.get(i)).removeFromTree(tree);
                }
            }
        }

        public void add(Node node) {
            insert(this.children.size, node);
        }

        public void addAll(Array<Node> nodes) {
            int n = nodes.size;
            for (int i = 0; i < n; i++) {
                insert(this.children.size, (Node) nodes.get(i));
            }
        }

        public void insert(int index, Node node) {
            node.parent = this;
            this.children.insert(index, node);
            updateChildren();
        }

        public void remove() {
            Tree tree = getTree();
            if (tree != null) {
                tree.remove(this);
            } else if (this.parent != null) {
                this.parent.remove(this);
            }
        }

        public void remove(Node node) {
            this.children.removeValue(node, true);
            if (this.expanded) {
                Tree tree = getTree();
                if (tree != null) {
                    node.removeFromTree(tree);
                    if (this.children.size == 0) {
                        this.expanded = false;
                    }
                }
            }
        }

        public void removeAll() {
            Tree tree = getTree();
            if (tree != null) {
                int n = this.children.size;
                for (int i = 0; i < n; i++) {
                    ((Node) this.children.get(i)).removeFromTree(tree);
                }
            }
            this.children.clear();
        }

        public Tree getTree() {
            Group parent = this.actor.getParent();
            if (parent instanceof Tree) {
                return (Tree) parent;
            }
            return null;
        }

        public Actor getActor() {
            return this.actor;
        }

        public boolean isExpanded() {
            return this.expanded;
        }

        public Array<Node> getChildren() {
            return this.children;
        }

        public void updateChildren() {
            if (this.expanded) {
                Tree tree = getTree();
                if (tree != null) {
                    int n = this.children.size;
                    for (int i = 0; i < n; i++) {
                        ((Node) this.children.get(i)).addToTree(tree);
                    }
                }
            }
        }

        public Node getParent() {
            return this.parent;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }

        public Object getObject() {
            return this.object;
        }

        public void setObject(Object object) {
            this.object = object;
        }

        public Drawable getIcon() {
            return this.icon;
        }

        public int getLevel() {
            int level = 0;
            Node current = this;
            do {
                level++;
                current = current.getParent();
            } while (current != null);
            return level;
        }

        public Node findNode(Object object) {
            if (object == null) {
                throw new IllegalArgumentException("object cannot be null.");
            } else if (object.equals(this.object)) {
                return this;
            } else {
                return Tree.findNode(this.children, object);
            }
        }

        public void collapseAll() {
            setExpanded(false);
            Tree.collapseAll(this.children);
        }

        public void expandAll() {
            setExpanded(true);
            if (this.children.size > 0) {
                Tree.expandAll(this.children);
            }
        }

        public void expandTo() {
            for (Node node = this.parent; node != null; node = node.parent) {
                node.setExpanded(true);
            }
        }

        public boolean isSelectable() {
            return this.selectable;
        }

        public void setSelectable(boolean selectable) {
            this.selectable = selectable;
        }

        public void findExpandedObjects(Array objects) {
            if (this.expanded && !Tree.findExpandedObjects(this.children, objects)) {
                objects.add(this.object);
            }
        }

        public void restoreExpandedObjects(Array objects) {
            int n = objects.size;
            for (int i = 0; i < n; i++) {
                Node node = findNode(objects.get(i));
                if (node != null) {
                    node.setExpanded(true);
                    node.expandTo();
                }
            }
        }
    }

    public static class TreeStyle {
        public Drawable background;
        public Drawable minus;
        public Drawable over;
        public Drawable plus;
        public Drawable selection;

        public TreeStyle(Drawable plus, Drawable minus, Drawable selection) {
            this.plus = plus;
            this.minus = minus;
            this.selection = selection;
        }

        public TreeStyle(TreeStyle style) {
            this.plus = style.plus;
            this.minus = style.minus;
            this.selection = style.selection;
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.Tree$1 */
    class C13391 extends ClickListener {
        C13391() {
        }

        public void clicked(InputEvent event, float x, float y) {
            Node node = Tree.this.getNodeAt(y);
            if (node == null || node != Tree.this.getNodeAt(getTouchDownY())) {
                return;
            }
            float low;
            if (Tree.this.selection.getMultiple() && Tree.this.selection.hasItems() && UIUtils.shift()) {
                low = ((Node) Tree.this.selection.getLastSelected()).actor.getY();
                float high = node.actor.getY();
                if (!UIUtils.ctrl()) {
                    Tree.this.selection.clear();
                }
                if (low > high) {
                    Tree.this.selectNodes(Tree.this.rootNodes, high, low);
                } else {
                    Tree.this.selectNodes(Tree.this.rootNodes, low, high);
                }
                Tree.this.selection.fireChangeEvent();
                return;
            }
            if (node.children.size > 0 && !(Tree.this.selection.getMultiple() && UIUtils.ctrl())) {
                low = node.actor.getX();
                if (node.icon != null) {
                    low -= Tree.this.iconSpacingRight + node.icon.getMinWidth();
                }
                if (x < low) {
                    node.setExpanded(node.expanded ^ 1);
                    return;
                }
            }
            if (node.isSelectable()) {
                Tree.this.selection.choose(node);
            }
        }

        public boolean mouseMoved(InputEvent event, float x, float y) {
            Tree.this.setOverNode(Tree.this.getNodeAt(y));
            return false;
        }

        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
            super.exit(event, x, y, pointer, toActor);
            if (toActor == null || !toActor.isDescendantOf(Tree.this)) {
                Tree.this.setOverNode(null);
            }
        }
    }

    public Tree(Skin skin) {
        this((TreeStyle) skin.get(TreeStyle.class));
    }

    public Tree(Skin skin, String styleName) {
        this((TreeStyle) skin.get(styleName, TreeStyle.class));
    }

    public Tree(TreeStyle style) {
        this.rootNodes = new Array();
        this.ySpacing = 4.0f;
        this.iconSpacingLeft = 2.0f;
        this.iconSpacingRight = 2.0f;
        this.padding = 0.0f;
        this.sizeInvalid = true;
        this.selection = new Selection();
        this.selection.setActor(this);
        this.selection.setMultiple(true);
        setStyle(style);
        initialize();
    }

    private void initialize() {
        EventListener c13391 = new C13391();
        this.clickListener = c13391;
        addListener(c13391);
    }

    public void setStyle(TreeStyle style) {
        this.style = style;
        this.indentSpacing = Math.max(style.plus.getMinWidth(), style.minus.getMinWidth()) + this.iconSpacingLeft;
    }

    public void add(Node node) {
        insert(this.rootNodes.size, node);
    }

    public void insert(int index, Node node) {
        remove(node);
        node.parent = null;
        this.rootNodes.insert(index, node);
        node.addToTree(this);
        invalidateHierarchy();
    }

    public void remove(Node node) {
        if (node.parent != null) {
            node.parent.remove(node);
            return;
        }
        this.rootNodes.removeValue(node, true);
        node.removeFromTree(this);
        invalidateHierarchy();
    }

    public void clearChildren() {
        super.clearChildren();
        setOverNode(null);
        this.rootNodes.clear();
        this.selection.clear();
    }

    public Array<Node> getNodes() {
        return this.rootNodes;
    }

    public void invalidate() {
        super.invalidate();
        this.sizeInvalid = true;
    }

    private void computeSize() {
        this.sizeInvalid = false;
        this.prefWidth = this.style.plus.getMinWidth();
        this.prefWidth = Math.max(this.prefWidth, this.style.minus.getMinWidth());
        this.prefHeight = getHeight();
        this.leftColumnWidth = 0.0f;
        computeSize(this.rootNodes, this.indentSpacing);
        this.leftColumnWidth += this.iconSpacingLeft + this.padding;
        this.prefWidth += this.leftColumnWidth + this.padding;
        this.prefHeight = getHeight() - this.prefHeight;
    }

    private void computeSize(Array<Node> nodes, float indent) {
        float ySpacing = this.ySpacing;
        float spacing = this.iconSpacingLeft + this.iconSpacingRight;
        int n = nodes.size;
        for (int i = 0; i < n; i++) {
            Node node = (Node) nodes.get(i);
            float rowWidth = this.iconSpacingRight + indent;
            Actor actor = node.actor;
            if (actor instanceof Layout) {
                Layout layout = (Layout) actor;
                rowWidth += layout.getPrefWidth();
                node.height = layout.getPrefHeight();
                layout.pack();
            } else {
                rowWidth += actor.getWidth();
                node.height = actor.getHeight();
            }
            if (node.icon != null) {
                rowWidth += node.icon.getMinWidth() + spacing;
                node.height = Math.max(node.height, node.icon.getMinHeight());
            }
            this.prefWidth = Math.max(this.prefWidth, rowWidth);
            this.prefHeight -= node.height + ySpacing;
            if (node.expanded) {
                computeSize(node.children, this.indentSpacing + indent);
            }
        }
    }

    public void layout() {
        if (this.sizeInvalid) {
            computeSize();
        }
        layout(this.rootNodes, (this.leftColumnWidth + this.indentSpacing) + this.iconSpacingRight, getHeight() - (this.ySpacing / 2.0f));
    }

    private float layout(Array<Node> nodes, float indent, float y) {
        float ySpacing = this.ySpacing;
        int n = nodes.size;
        for (int i = 0; i < n; i++) {
            Node node = (Node) nodes.get(i);
            Actor actor = node.actor;
            float x = indent;
            if (node.icon != null) {
                x += node.icon.getMinWidth();
            }
            y -= node.height;
            node.actor.setPosition(x, y);
            y -= ySpacing;
            if (node.expanded) {
                y = layout(node.children, this.indentSpacing + indent, y);
            }
        }
        return y;
    }

    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.f4r, color.f3g, color.f2b, color.f1a * parentAlpha);
        if (this.style.background != null) {
            this.style.background.draw(batch, getX(), getY(), getWidth(), getHeight());
        }
        draw(batch, this.rootNodes, this.leftColumnWidth);
        super.draw(batch, parentAlpha);
    }

    private void draw(Batch batch, Array<Node> nodes, float indent) {
        Batch batch2 = batch;
        Array array = nodes;
        Drawable plus = this.style.plus;
        Drawable minus = this.style.minus;
        float x = getX();
        float y = getY();
        int n = array.size;
        int i = 0;
        while (true) {
            int n2 = n;
            if (i < n2) {
                Actor actor;
                Drawable plus2;
                Node node = (Node) array.get(i);
                Actor actor2 = node.actor;
                if (!r0.selection.contains(node) || r0.style.selection == null) {
                    actor = actor2;
                    plus2 = plus;
                    plus = node;
                    if (plus == r0.overNode && r0.style.over != null) {
                        r0.style.over.draw(batch2, x, (actor.getY() + y) - (r0.ySpacing / 2.0f), getWidth(), plus.height + r0.ySpacing);
                    }
                } else {
                    actor = actor2;
                    plus2 = plus;
                    plus = node;
                    r0.style.selection.draw(batch2, x, (actor2.getY() + y) - (r0.ySpacing / 2.0f), getWidth(), node.height + r0.ySpacing);
                }
                if (plus.icon != null) {
                    float iconY = actor.getY() + ((float) Math.round((plus.height - plus.icon.getMinHeight()) / 2.0f));
                    batch2.setColor(actor.getColor());
                    plus.icon.draw(batch2, ((plus.actor.getX() + x) - r0.iconSpacingRight) - plus.icon.getMinWidth(), y + iconY, plus.icon.getMinWidth(), plus.icon.getMinHeight());
                    batch2.setColor(Color.WHITE);
                }
                if (plus.children.size != 0) {
                    Drawable expandIcon = plus.expanded ? minus : plus2;
                    expandIcon.draw(batch2, (x + indent) - r0.iconSpacingLeft, y + (actor.getY() + ((float) Math.round((plus.height - expandIcon.getMinHeight()) / 2.0f))), expandIcon.getMinWidth(), expandIcon.getMinHeight());
                    if (plus.expanded) {
                        draw(batch2, plus.children, indent + r0.indentSpacing);
                    }
                }
                i++;
                n = n2;
                plus = plus2;
                Array<Node> array2 = nodes;
            } else {
                return;
            }
        }
    }

    public Node getNodeAt(float y) {
        this.foundNode = null;
        getNodeAt(this.rootNodes, y, getHeight());
        return this.foundNode;
    }

    private float getNodeAt(Array<Node> nodes, float y, float rowY) {
        int i = 0;
        int n = nodes.size;
        while (i < n) {
            Node node = (Node) nodes.get(i);
            if (y < (rowY - node.height) - this.ySpacing || y >= rowY) {
                rowY -= node.height + this.ySpacing;
                if (node.expanded) {
                    rowY = getNodeAt(node.children, y, rowY);
                    if (rowY == -1.0f) {
                        return -1.0f;
                    }
                }
                i++;
            } else {
                this.foundNode = node;
                return -1.0f;
            }
        }
        return rowY;
    }

    void selectNodes(Array<Node> nodes, float low, float high) {
        int i = 0;
        int n = nodes.size;
        while (i < n) {
            Node node = (Node) nodes.get(i);
            if (node.actor.getY() >= low) {
                if (node.isSelectable()) {
                    if (node.actor.getY() <= high) {
                        this.selection.add(node);
                    }
                    if (node.expanded) {
                        selectNodes(node.children, low, high);
                    }
                }
                i++;
            } else {
                return;
            }
        }
    }

    public Selection<Node> getSelection() {
        return this.selection;
    }

    public TreeStyle getStyle() {
        return this.style;
    }

    public Array<Node> getRootNodes() {
        return this.rootNodes;
    }

    public Node getOverNode() {
        return this.overNode;
    }

    public void setOverNode(Node overNode) {
        this.overNode = overNode;
    }

    public void setPadding(float padding) {
        this.padding = padding;
    }

    public float getIndentSpacing() {
        return this.indentSpacing;
    }

    public void setYSpacing(float ySpacing) {
        this.ySpacing = ySpacing;
    }

    public void setIconSpacing(float left, float right) {
        this.iconSpacingLeft = left;
        this.iconSpacingRight = right;
    }

    public float getPrefWidth() {
        if (this.sizeInvalid) {
            computeSize();
        }
        return this.prefWidth;
    }

    public float getPrefHeight() {
        if (this.sizeInvalid) {
            computeSize();
        }
        return this.prefHeight;
    }

    public void findExpandedObjects(Array objects) {
        findExpandedObjects(this.rootNodes, objects);
    }

    public void restoreExpandedObjects(Array objects) {
        int n = objects.size;
        for (int i = 0; i < n; i++) {
            Node node = findNode(objects.get(i));
            if (node != null) {
                node.setExpanded(true);
                node.expandTo();
            }
        }
    }

    static boolean findExpandedObjects(Array<Node> nodes, Array objects) {
        int n = nodes.size;
        for (int i = 0; i < n; i++) {
            Node node = (Node) nodes.get(i);
            if (node.expanded && !findExpandedObjects(node.children, objects)) {
                objects.add(node.object);
            }
        }
        return false;
    }

    public Node findNode(Object object) {
        if (object != null) {
            return findNode(this.rootNodes, object);
        }
        throw new IllegalArgumentException("object cannot be null.");
    }

    static Node findNode(Array<Node> nodes, Object object) {
        int i;
        int n = nodes.size;
        for (i = 0; i < n; i++) {
            Node node = (Node) nodes.get(i);
            if (object.equals(node.object)) {
                return node;
            }
        }
        n = nodes.size;
        for (i = 0; i < n; i++) {
            Node found = findNode(((Node) nodes.get(i)).children, object);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    public void collapseAll() {
        collapseAll(this.rootNodes);
    }

    static void collapseAll(Array<Node> nodes) {
        int n = nodes.size;
        for (int i = 0; i < n; i++) {
            Node node = (Node) nodes.get(i);
            node.setExpanded(false);
            collapseAll(node.children);
        }
    }

    public void expandAll() {
        expandAll(this.rootNodes);
    }

    static void expandAll(Array<Node> nodes) {
        int n = nodes.size;
        for (int i = 0; i < n; i++) {
            ((Node) nodes.get(i)).expandAll();
        }
    }

    public ClickListener getClickListener() {
        return this.clickListener;
    }
}

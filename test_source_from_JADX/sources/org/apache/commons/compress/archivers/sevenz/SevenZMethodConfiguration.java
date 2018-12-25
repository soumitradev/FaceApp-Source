package org.apache.commons.compress.archivers.sevenz;

public class SevenZMethodConfiguration {
    private final SevenZMethod method;
    private final Object options;

    public SevenZMethodConfiguration(SevenZMethod method) {
        this(method, null);
    }

    public SevenZMethodConfiguration(SevenZMethod method, Object options) {
        this.method = method;
        this.options = options;
        if (options != null && !Coders.findByMethod(method).canAcceptOptions(options)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("The ");
            stringBuilder.append(method);
            stringBuilder.append(" method doesn't support options of type ");
            stringBuilder.append(options.getClass());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public SevenZMethod getMethod() {
        return this.method;
    }

    public Object getOptions() {
        return this.options;
    }
}

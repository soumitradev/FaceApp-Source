package name.antonsmirnov.firmata.wrapper;

public class AndFilter implements IMessageFilter {
    private IMessageFilter[] filters;

    public AndFilter(IMessageFilter... filters) {
        this.filters = filters;
    }

    public boolean isAllowed(MessageWithProperties data) {
        for (IMessageFilter eachFilter : this.filters) {
            if (!eachFilter.isAllowed(data)) {
                return false;
            }
        }
        return true;
    }
}

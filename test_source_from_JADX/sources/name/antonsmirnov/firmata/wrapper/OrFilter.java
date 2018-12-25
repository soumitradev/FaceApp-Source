package name.antonsmirnov.firmata.wrapper;

public class OrFilter implements IMessageFilter {
    private IMessageFilter[] filters;

    public OrFilter(IMessageFilter... filters) {
        this.filters = filters;
    }

    public boolean isAllowed(MessageWithProperties data) {
        for (IMessageFilter eachFilter : this.filters) {
            if (eachFilter.isAllowed(data)) {
                return true;
            }
        }
        return false;
    }
}

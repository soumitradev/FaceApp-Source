package android.support.v7.app;

class AppCompatDelegateImplV9$1 implements Runnable {
    final /* synthetic */ AppCompatDelegateImplV9 this$0;

    AppCompatDelegateImplV9$1(AppCompatDelegateImplV9 this$0) {
        this.this$0 = this$0;
    }

    public void run() {
        if ((this.this$0.mInvalidatePanelMenuFeatures & 1) != 0) {
            this.this$0.doInvalidatePanelMenu(0);
        }
        if ((this.this$0.mInvalidatePanelMenuFeatures & 4096) != 0) {
            this.this$0.doInvalidatePanelMenu(108);
        }
        this.this$0.mInvalidatePanelMenuPosted = false;
        this.this$0.mInvalidatePanelMenuFeatures = 0;
    }
}

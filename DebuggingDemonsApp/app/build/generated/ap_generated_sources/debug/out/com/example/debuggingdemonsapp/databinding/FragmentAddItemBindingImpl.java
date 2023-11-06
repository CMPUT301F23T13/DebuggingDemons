package com.example.debuggingdemonsapp.databinding;
import com.example.debuggingdemonsapp.R;
import com.example.debuggingdemonsapp.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class FragmentAddItemBindingImpl extends FragmentAddItemBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.editTextDateOfPurchase, 1);
        sViewsWithIds.put(R.id.editTextDescription, 2);
        sViewsWithIds.put(R.id.editTextMake, 3);
        sViewsWithIds.put(R.id.editTextModel, 4);
        sViewsWithIds.put(R.id.editTextSerialNumber, 5);
        sViewsWithIds.put(R.id.editTextEstimatedValue, 6);
        sViewsWithIds.put(R.id.editTextComment, 7);
        sViewsWithIds.put(R.id.textView, 8);
        sViewsWithIds.put(R.id.imageButton, 9);
        sViewsWithIds.put(R.id.imageButton2, 10);
        sViewsWithIds.put(R.id.imageButton3, 11);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public FragmentAddItemBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds));
    }
    private FragmentAddItemBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (android.widget.EditText) bindings[7]
            , (android.widget.EditText) bindings[1]
            , (android.widget.EditText) bindings[2]
            , (android.widget.EditText) bindings[6]
            , (android.widget.EditText) bindings[3]
            , (android.widget.EditText) bindings[4]
            , (android.widget.EditText) bindings[5]
            , (android.widget.LinearLayout) bindings[0]
            , (android.widget.ImageButton) bindings[9]
            , (android.widget.ImageButton) bindings[10]
            , (android.widget.ImageButton) bindings[11]
            , (android.widget.TextView) bindings[8]
            );
        this.fragmentContainer.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
            return variableSet;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}
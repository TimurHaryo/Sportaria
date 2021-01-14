package id.train.sportaria.abstaction

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<B: ViewDataBinding> : AppCompatActivity() {
    private lateinit var mViewDataBinding: B

    @LayoutRes
    protected abstract fun resourceLayoutId(): Int
    protected abstract fun initView()

    val binding: B
        get() = mViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewDataBinding = DataBindingUtil.setContentView(this, resourceLayoutId())
        mViewDataBinding.lifecycleOwner = this

        initView()
    }
}
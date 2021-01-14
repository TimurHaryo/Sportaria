package id.train.sportaria.util.binding

import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@BindingAdapter("setLoadingSRL")
fun SwipeRefreshLayout.setLoadingSRL(status: Boolean?) {
    if (status == null) return
    isRefreshing = status
}
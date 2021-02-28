package todo.quang.mvvm.ui.post.adapter

import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_category.view.*
import todo.quang.mvvm.R
import todo.quang.mvvm.base.BaseAdapter
import todo.quang.mvvm.ui.post.PostListViewModel

class CategoryAdapter(
        private val packageManager: PackageManager,
        private val openApp: (namePackage: String) -> Unit
) : BaseAdapter<List<PostListViewModel.AppInfoDataItem>>(object : DiffUtil.ItemCallback<List<PostListViewModel.AppInfoDataItem>>() {
    override fun areItemsTheSame(oldItem: List<PostListViewModel.AppInfoDataItem>, newItem: List<PostListViewModel.AppInfoDataItem>): Boolean {
        return false
    }

    override fun areContentsTheSame(
            oldItem: List<PostListViewModel.AppInfoDataItem>,
            newItem: List<PostListViewModel.AppInfoDataItem>
    ): Boolean {
        return false
    }
}) {
    override fun createView(parent: ViewGroup, viewType: Int?): View {
        return parent.inflate(R.layout.item_category)
    }

    override fun bind(view: View, viewType: Int, position: Int, item: List<PostListViewModel.AppInfoDataItem>) {
        var itemApp = item[0]
        Glide
                .with(view)
                .load(itemApp.packageInfo.applicationInfo.loadIcon(packageManager))
                .centerInside()
                .into(view.imgFirst)
        view.imgFirst.setOnClickListener {
            openApp.invoke(item[0].packageInfo.packageName)
        }
        view.tvTitle.text = itemApp.appInfoEntity.genre
        if (item.size > 1) {
            itemApp = item[1]
            Glide
                    .with(view)
                    .load(itemApp.packageInfo.applicationInfo.loadIcon(packageManager))
                    .centerInside()
                    .into(view.imgSecond)
            view.imgSecond.setOnClickListener {
                openApp.invoke(item[1].packageInfo.packageName)
            }
        }
        if (item.size > 2) {
            itemApp = item[2]
            Glide
                    .with(view)
                    .load(itemApp.packageInfo.applicationInfo.loadIcon(packageManager))
                    .centerInside()
                    .into(view.imgThird)
            view.imgThird.setOnClickListener {
                openApp.invoke(item[2].packageInfo.packageName)
            }
        }
        if (item.size > 3) {
            itemApp = item[3]
            Glide
                    .with(view)
                    .load(itemApp.packageInfo.applicationInfo.loadIcon(packageManager))
                    .centerInside()
                    .into(view.imgFourth)
            view.imgFourth.setOnClickListener {
                openApp.invoke(item[3].packageInfo.packageName)
            }
        }
    }

    fun ViewGroup.inflate(@LayoutRes l: Int): View {
        return LayoutInflater.from(context).inflate(l, this, false)
    }
}
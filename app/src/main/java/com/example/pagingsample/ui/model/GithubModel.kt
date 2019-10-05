package com.example.pagingsample.ui.model

import android.view.LayoutInflater
import android.view.View
import androidx.annotation.CallSuper
import androidx.databinding.DataBindingUtil
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.pagingsample.R
import com.example.pagingsample.data.GithubRepo
import com.example.pagingsample.databinding.ItemGithubBinding

@EpoxyModelClass(layout = R.layout.item_github)
abstract class GithubModel : EpoxyModelWithHolder<GithubModel.ViewHolder>() {

    @EpoxyAttribute
    lateinit var repo: GithubRepo

    override fun bind(holder: ViewHolder) {
        holder.binding.repo = repo
    }

    inner class ViewHolder : EpoxyHolder() {

        override fun bindView(itemView: View) {
            binding = DataBindingUtil.bind(itemView) ?: return
        }
        lateinit var binding: ItemGithubBinding
    }
}
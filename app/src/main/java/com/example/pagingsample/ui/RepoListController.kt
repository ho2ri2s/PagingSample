package com.example.pagingsample.ui

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.example.pagingsample.data.GithubRepo
import com.example.pagingsample.ui.model.GithubModel_

class RepoListController : PagedListEpoxyController<GithubRepo>() {

    override fun buildItemModel(currentPosition: Int, item: GithubRepo?): EpoxyModel<*> {
        return GithubModel_().apply {
            id("githubRepository")
            repo = item ?: GithubRepo(id = "0", name = "", url = "", star = "0")
        }
    }

}
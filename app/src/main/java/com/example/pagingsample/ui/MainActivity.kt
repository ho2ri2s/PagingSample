package com.example.pagingsample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pagingsample.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        val controller = RepoListController()

        recycler_view.run {
            this.adapter = controller.adapter
            this.layoutManager = LinearLayoutManager(this@MainActivity)
            this.addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                    DividerItemDecoration.VERTICAL
                )
            )
            controller.requestModelBuild()
        }
        viewModel.repositories.observe(this, Observer {
            controller.submitList(it)
        })
        viewModel.networkState.observe(this, Observer {
            Log.d("MYTAG", "$it")
        })

    }
}

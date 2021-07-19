package com.appknot.cardgame.ui.activity

import android.content.Intent
import android.view.View
import com.appknot.bentleymembership.base.BaseActivity
import com.appknot.cardgame.R
import com.appknot.cardgame.databinding.ActivityMainBinding
import com.appknot.cardgame.viewmodel.MainViewModel
import com.appknot.core_rx.extensions.intentFor
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 게임 시작 전 홈 화면
 * @author Catherine on 2021-07-14
 */
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val layoutResourceId: Int = R.layout.activity_main
    override val viewModel: MainViewModel by viewModel()

    override fun initAfterBinding() {
    }

    override fun initDataBinding() {
        viewDataBinding.run {
            main = this@MainActivity
        }
    }

    override fun initStartView() {
    }

    fun startPlay(view: View) {
        val intent = Intent(this, PlayActivity::class.java)
        intent.apply {
            when (view.id) {
                R.id.btn_44 -> {
                    putExtra(KEY_INTENT_COLUMNS, 4)
                    putExtra(KEY_INTENT_ROWS, 4)
                }
                R.id.btn_56 -> {
                    putExtra(KEY_INTENT_COLUMNS, 5)
                    putExtra(KEY_INTENT_ROWS, 6)
                }
                R.id.btn_67 -> {
                    putExtra(KEY_INTENT_COLUMNS, 6)
                    putExtra(KEY_INTENT_ROWS, 7)
                }
            }
        }
        startActivity(intent)
    }
}
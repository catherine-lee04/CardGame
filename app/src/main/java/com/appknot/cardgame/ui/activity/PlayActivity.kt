package com.appknot.cardgame.ui.activity

import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.appknot.bentleymembership.base.BaseActivity
import com.appknot.cardgame.R
import com.appknot.cardgame.databinding.ActivityPlayBinding
import com.appknot.cardgame.ui.adapter.CardAdapter
import com.appknot.cardgame.viewmodel.PlayViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.ArrayList

/**
 * 게임 실행 화면
 * @author Catherine on 2021-07-14
 */
class PlayActivity : BaseActivity<ActivityPlayBinding, PlayViewModel>() {
    override val layoutResourceId: Int = R.layout.activity_play
    override val viewModel: PlayViewModel by viewModel()
    private val columns: Int by lazy {
        intent.getIntExtra(KEY_INTENT_COLUMNS, 4)
    }
    private val rows: Int by lazy {
        intent.getIntExtra(KEY_INTENT_ROWS, 4)
    }

    override fun initAfterBinding() {
    }

    override fun initDataBinding() {
        viewDataBinding.run {
            play = this@PlayActivity
            vm = viewModel
        }

        viewModel.matchedCount.observe(this@PlayActivity, Observer {
            // 모든 카드를 맞춘 경우
            if (it >= (columns * rows / 2)) {
                AlertDialog.Builder(this@PlayActivity)
                    .setMessage(getString(R.string.play_all_matched))
                    .setPositiveButton(R.string.alert_ok) { _, _ ->
                        finish()
                    }
                    .create()
                    .show()
            }
        })
    }

    override fun initStartView() {
        viewDataBinding.rvCard.layoutManager = GridLayoutManager(this, columns)
        initCard()
    }

    /**
     * 카드 설정
     */
    private fun initCard() {
        // 설정할 카드를 랜덤으로 설정
        val cardIndex = ArrayList<Int>()
        cardIndex.clear()
        while (cardIndex.size < columns * rows) {
            val index = getRandomIndex()
            if (!cardIndex.contains(index)) {
                cardIndex.add(index)
                cardIndex.add(index)
            }
        }
        cardIndex.shuffle()

        // 해당 카드의 Resource ID 값 가져오기
        viewModel.run {
            cardResource.clear()
            cardIndex.forEach {
                cardResource.add(
                    resources.getIdentifier(
                        "img_card_$it",
                        "drawable",
                        packageName))
            }
        }
    }

    private fun getRandomIndex(): Int {
        return Random().nextInt(21) + 1
    }
}
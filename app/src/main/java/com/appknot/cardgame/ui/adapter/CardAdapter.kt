package com.appknot.cardgame.ui.adapter

import android.content.res.Resources
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import com.appknot.bentleymembership.base.BaseRecyclerViewAdapter
import com.appknot.cardgame.R
import com.appknot.cardgame.databinding.ItemCardBinding
import com.google.gson.Gson

/**
 *
 * @author Catherine on 2021-07-14
 */
class CardAdapter(
    private val items: ArrayList<Int>,
    private val tryAction: (() -> Unit),
    private val matchedAction: (() -> Unit)
) : BaseRecyclerViewAdapter<ItemCardBinding>() {
    override val layoutRes: Int = R.layout.item_card

    private val ANIMATION_DURATION = 150L
    private val isOpened = HashMap<Int, Int>()
    private var lastOpened = -1
    private var isMatched = false

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder<ItemCardBinding>, position: Int) {
        with (holder) {
            bind.ivCard.setImageResource(items[position])
            bind.ivFront.isVisible = !isOpened.containsKey(position)

            // 선택했던 카드에 대한 애니메이션 표시
            if (isOpened.containsKey(position)) {
                if (isMatched) {
                    // 카드를 맞춘 경우 선택한 카드 가리기
                    hideAnimation(bind.ivFront, bind.ivCard)
                } else {
                    // 카드를 맞추지 못한 경우 선택한 카드 다시 뒤집기
                    closeAnimation(bind.ivFront, bind.ivCard)
                }

                isOpened.remove(position)

                // 선택했던 두 장의 카드에 대한 애니메이션을 끝낸 경우 상태 초기화
                if (isOpened.values.isEmpty()) {
                    isMatched = false
                }
            }

            itemView.setOnClickListener {
                if (bind.ivCard.alpha >= 1f && lastOpened != position) {
                    // 2개 이상 선택하지 못하도록 방지
                    if (isOpened.keys.size < 2) {
                        isOpened[position] = items[position]

                        openAnimation(bind.ivFront, bind.ivCard)

                        if (lastOpened > -1) {
                            isMatched = isOpened[lastOpened] == isOpened[position]
                            Handler(Looper.getMainLooper()).postDelayed({
                                notifyItemChanged(lastOpened)
                                notifyItemChanged(position)

                                lastOpened = -1

                                // 시도 횟수 증가
                                tryAction.invoke()
                                if (isMatched) {
                                    // 맞춘 횟수 증가
                                    matchedAction.invoke()
                                }
                            }, 500)
                        } else {
                            lastOpened = position
                        }
                    }
                }
            }
        }
    }

    /**
     * 선택한 카드 뒤집기
     */
    private fun openAnimation(fView: View, cView: ImageView) {
        fView.animate()
            .scaleXBy(1f)
            .scaleX(0f)
            .setStartDelay(0L)
            .setDuration(ANIMATION_DURATION)
            .withEndAction {
                fView.isVisible = false

                cView.animate()
                    .scaleXBy(0f)
                    .scaleX(1f)
                    .setStartDelay(0L)
                    .setDuration(ANIMATION_DURATION)
                    .start()
            }
            .start()
    }

    /**
     * 실패 시 다시 원래대로 뒤집기
     */
    private fun closeAnimation(fView: View, cView: ImageView) {
        cView.animate()
            .scaleXBy(1f)
            .scaleX(0f)
            .setStartDelay(0L)
            .setDuration(ANIMATION_DURATION)
            .withEndAction {
                fView.isVisible = true
                fView.animate()
                    .scaleXBy(0f)
                    .scaleX(1f)
                    .setStartDelay(0L)
                    .setDuration(ANIMATION_DURATION)
                    .start()
            }
            .start()
    }

    /**
     * 성공 시 해당 카드 숨기기
     */
    private fun hideAnimation(fView: View, cView: ImageView) {
        cView.animate()
            .alphaBy(1f)
            .alpha(0f)
            .setStartDelay(0L)
            .setDuration(ANIMATION_DURATION)
            .withStartAction {
                fView.animate()
                    .alphaBy(0f)
                    .alpha(1f)
                    .setStartDelay(0L)
                    .setDuration(ANIMATION_DURATION)
                    .start()
            }
            .start()
    }
}
package com.peacemaker.android.spare.ui.main.wallet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.peacemaker.android.spare.R

class CreditCardAdapter(private val cards: List<CreditCard>) : RecyclerView.Adapter<CreditCardAdapter.CreditCardViewHolder>() {

    inner class CreditCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardType:TextView = itemView.findViewById(R.id.type)
        val cardNumber: TextView = itemView.findViewById(R.id.cardNumber)
        val cardHolderName: TextView = itemView.findViewById(R.id.holderName)
        val cardExpiryDate: TextView = itemView.findViewById(R.id.expiryDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditCardViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.credit_card_layout, parent, false)
        return CreditCardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CreditCardViewHolder, position: Int) {
        val card = cards[position]
        holder.cardType.text = card.cardType
        holder.cardNumber.text = card.cardNumber
        holder.cardHolderName.text = card.cardHolderName
        holder.cardExpiryDate.text = card.cardExpiryDate
    }

    override fun getItemCount(): Int {
        return cards.size
    }
}

package com.peacemaker.android.spare.ui.main.transfer

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.peacemaker.android.spare.R
import com.peacemaker.android.spare.data.*
import com.peacemaker.android.spare.data.crud.findUserByName
import com.peacemaker.android.spare.data.crud.getCollection
import com.peacemaker.android.spare.data.crud.updateDocument
import com.peacemaker.android.spare.databinding.FragmentTransferBinding
import com.peacemaker.android.spare.databinding.TransferSuccessDialogBinding
import com.peacemaker.android.spare.ui.util.BaseFragment
import kotlinx.coroutines.runBlocking
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class TransferFragment : BaseFragment() {
    private var _binding: FragmentTransferBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TransferViewModel
    var value: Double = 0.0
    private var dialog: AlertDialog? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransferBinding.inflate(layoutInflater)
        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[TransferViewModel::class.java]
        binding.send.setOnClickListener {
            transactions()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun transactions() {
        val editText = binding.transferAmt.text
        if (editText.isBlank())
            showSnackBar(requireView(), "Field cannot be empty")
        else {
            value = editText.toString().toDoubleOrNull()!!
            runBlocking {
                val userList = getCollection("users", User::class.java)
                Log.d(TAG, "userList::::::::::::::::::::$userList")

                val sender: User? =
                    findUserByName(userList, "Kwesi Welbred")
                val senderBal = sender?.accBalance?.toDouble()?.minus(value)

                val receiver: User? = findUserByName(userList, "Peacemaker Otoo")
                val receiverBal = receiver?.accBalance?.toDouble()?.plus(value)

                val db = FirebaseFirestore.getInstance()
                val usersRef = db.collection("users")
                val fieldName = "accBalance"

                //update receiver
                updateDocument(
                    collectionRef = usersRef,
                    documentId = receiver?.id!!, fieldName, receiverBal
                ) {

                }

                //update sender amt
                updateDocument(
                    collectionRef = usersRef,
                    documentId = sender?.id!!, fieldName, senderBal
                ) {

                }

                val formatter = DateTimeFormatter.ofPattern("yyyy-MMMM-dd")
                val currentDate = LocalDate.now().format(formatter)

                //save transaction
                val txId = UUID.randomUUID().toString()
                val transaction = Transaction(
                    id = txId,
                    sender = findUserByName(userList, "Kwesi Welbred"),
                    receiver = findUserByName(userList, "Peacemaker Otoo"),
                    amount = value,
                    currency = "EUR",
                    date = currentDate,
                    description = "Payment for rent",
                    status = TransactionStatus.PENDING,
                    type = TransactionType.SEND
                )

                db.collection("transactions")
                    .document(transaction.id)
                    .set(transaction)
                    .addOnSuccessListener {
                        showSuccessTransfer()
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding transaction", e)
                    }

            }
        }

    }

    @SuppressLint("SetTextI18n")
    private fun showSuccessTransfer() {
        dialog = inflateViewBindingDialog(TransferSuccessDialogBinding::inflate) { dialogBinding ->
            dialogBinding.amtSent.text = "You successfully sent $value to"
            dialogBinding.login.setOnClickListener {
                findNavController().navigate(R.id.action_transferFragment_to_bottom_nav_graph)
                dialog?.dismiss()
            }
            dialogBinding.sendMore.setOnClickListener {
                dialog?.dismiss()
            }
        }
        dialog!!.show()
    }
}
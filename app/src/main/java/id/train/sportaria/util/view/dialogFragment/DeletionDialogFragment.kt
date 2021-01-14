package id.train.sportaria.util.view.dialogFragment

import id.train.sportaria.R
import id.train.sportaria.abstaction.BaseDialogFragment
import id.train.sportaria.databinding.DialogFragmentDeletionBinding

class DeletionDialogFragment(
    private val onDelete: () -> Unit
) : BaseDialogFragment<DialogFragmentDeletionBinding>() {

    override fun resourceLayoutId(): Int = R.layout.dialog_fragment_deletion

    override fun initViewCreated() {
        with(binding) {
            btnDeletionCancel.setOnClickListener { dismiss() }
            btnDeletionDeleteItem.setOnClickListener {
                onDelete()
                dismiss()
            }
        }
    }

    companion object {
        fun newInstance(onDelete: () -> Unit) = DeletionDialogFragment(onDelete)
    }
}
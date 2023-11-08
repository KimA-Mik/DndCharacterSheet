package ru.kima.dndcharactersheet.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.DialogFragment
import ru.kima.dndcharactersheet.R
import ru.kima.dndcharactersheet.databinding.DialogEditNumberValueBinding

class EditNumberDialog : DialogFragment() {
    private var _currentVal = 0
    private var _minValue: Int = 1
    private var _maxValue: Int = 30
    private var _step: Int = 1
    private var _strongStep: Int = 5
    private var _title = String()
    private var _titleId: Int = 0

//    interface EditNumberDialogListener {
//        fun onDialogPositiveClick(dialog: DialogFragment)
//        fun onDialogNegativeClick(dialog: DialogFragment)
//    }
//    private lateinit var listener: EditNumberDialogListener


    fun config(
        titleId: Int,
        initialValue: Int,
        minValue: Int = 1,
        maxValue: Int = 30,
        step: Int = 1,
        strongStep: Int = 5
    ) {
        _titleId = titleId
        configValues(initialValue, minValue, maxValue, step, strongStep)
    }

    fun config(
        title: String,
        initialValue: Int,
        minValue: Int = 1,
        maxValue: Int = 30,
        step: Int = 1,
        strongStep: Int = 5
    ) {
        _title = title
        configValues(initialValue, minValue, maxValue, step, strongStep)
    }

    private fun configValues(
        initialValue: Int, minValue: Int, maxValue: Int, step: Int, strongStep: Int
    ) {
        _currentVal = initialValue
        _minValue = minValue
        _maxValue = maxValue
        _step = step
        _strongStep = strongStep
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("titleId", _titleId)
        outState.putString("title", _title)

        outState.putInt("currentVal", _currentVal)
        outState.putInt("minValue", _minValue)
        outState.putInt("maxValue", _maxValue)
        outState.putInt("step", _step)
        outState.putInt("strongStep", _strongStep)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        attachListener()
        if (savedInstanceState != null) {
            _titleId = savedInstanceState.getInt("titleId")
            _title = savedInstanceState.getString("title").toString()

            _currentVal = savedInstanceState.getInt("currentVal")
            _minValue = savedInstanceState.getInt("minValue")
            _maxValue = savedInstanceState.getInt("maxValue")
            _step = savedInstanceState.getInt("step")
            _strongStep = savedInstanceState.getInt("strongStep")
        }

        val builder = AlertDialog.Builder(requireContext())
        val binding = DialogEditNumberValueBinding.inflate(layoutInflater)
        binding.characteristicEditTextEdit.setText(_currentVal.toString())
        binding.characteristicEditTextEdit.addTextChangedListener(
            object : TextWatcher {
                val minStr = _minValue.toString()
                val maxStr = _maxValue.toString()
                private var newValue = String()
                private var isOk = true
                private var _ignore = false

                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    if (p0 == null) {
                        return
                    }

                    val numStr = p0.toString()
                    if (numStr.isEmpty()) {
                        setLeftBorder()
                        return
                    }

                    val num = numStr.toInt()
                    if (num < _minValue) {
                        setLeftBorder()
                        return
                    } else if (num > _maxValue) {
                        setRightBorder()
                        return
                    }
                    _currentVal = num
                }

                override fun afterTextChanged(p0: Editable?) {
                    if (_ignore ||
                        isOk
                    ) {
                        return
                    }
                    isOk = true
                    _ignore = true
                    binding.characteristicEditTextEdit.setText(newValue)
                    _ignore = false
                }

                private fun setLeftBorder() {
                    isOk = false
                    newValue = minStr
                    _currentVal = _minValue
                }

                private fun setRightBorder() {
                    isOk = false
                    newValue = maxStr
                    _currentVal = _maxValue
                }
            }
        )

        binding.increaseButton.setOnClickListener {
//            val newVal = max(_currentVal + _step, _maxValue)
            val newVal = _currentVal + _step
            binding.characteristicEditTextEdit.setText(newVal.toString())
        }

        binding.strongIncreaseButton.setOnClickListener {
//            val newVal = max(_currentVal + _step, _maxValue)
            val newVal = _currentVal + _strongStep
            binding.characteristicEditTextEdit.setText(newVal.toString())
        }

        binding.decreaseButton.setOnClickListener {
            val newVal = _currentVal - _step
            binding.characteristicEditTextEdit.setText(newVal.toString())
        }

        binding.strongDecreaseButton.setOnClickListener {
            val newVal = _currentVal - _strongStep
            binding.characteristicEditTextEdit.setText(newVal.toString())
        }


        if (_titleId == 0) {
            builder.setTitle(_title)
        } else {
            builder.setTitle(_titleId)
        }
        builder.setView(binding.root)
            .setPositiveButton(R.string.dialog_accept) { _, _ ->
                val result = Bundle()
                result.putBoolean(DIALOG_SUCCESS_FLAG, true)
                result.putInt(EDIT_NUMBER_DIALOG_REQUEST, _currentVal)
                parentFragmentManager.setFragmentResult(EDIT_NUMBER_DIALOG_REQUEST_KEY, result)
//                listener.onDialogPositiveClick(this)
            }
            .setNegativeButton(R.string.dialog_cancel) { _, _ ->
                val result = Bundle()
                result.putBoolean(DIALOG_SUCCESS_FLAG, false)
                parentFragmentManager.setFragmentResult(EDIT_NUMBER_DIALOG_REQUEST_KEY, result)
//                listener.onDialogNegativeClick(this)
            }


        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(R.drawable.layout_dialog_background)
        return dialog
    }


    //For direct calls from fragment
//    private fun attachListener() {
//        try {
//            listener = requireParentFragment().context as EditNumberDialogListener
//        } catch (e: ClassCastException) {
//            throw ClassCastException(
//                (requireParentFragment().context.toString() +
//                        " must implement EditNumberDialogListener")
//            )
//        }
//    }
}
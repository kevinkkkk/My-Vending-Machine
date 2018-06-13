package com.example.wkuai.myvendingmachine.views;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wkuai.myvendingmachine.R;
import com.example.wkuai.myvendingmachine.adapter.SummaryAdapter;
import com.example.wkuai.myvendingmachine.models.AcceptedCoins;
import com.example.wkuai.myvendingmachine.models.PurchaseHistory;
import com.example.wkuai.myvendingmachine.panels.VendingMachineFragPanel;
import com.example.wkuai.myvendingmachine.presenters.VendingMachineFragPresenter;
import com.example.wkuai.myvendingmachine.utilts.VendingUtilts;

import java.util.List;

public class VendingMachineFragment extends Fragment implements VendingMachineFragPanel{

    TextView item1Counter;
    TextView item2Counter;
    TextView item3Counter;
    Button nickelBtn;
    Button dimeBtn;
    Button quarterBtn;
    TextView fundView;
    Button item1Btn;
    Button item2Btn;
    Button item3Btn;
    TextView itemIdView;
    Button abortBtn;
    Button confirmBtn;
    Button resetBtn;
    TextView returnFundsView;
    ImageView itemImageView;
    Button summarBtn;

    VendingMachineFragPresenter presenter;

    /**
     * Defines all listeners
     */
    View.OnClickListener onCoinClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case (R.id.btn_coin1):
                    presenter.insertCoin(AcceptedCoins.NICKELS);
                    break;

                case (R.id.btn_coin2):
                    presenter.insertCoin(AcceptedCoins.DIMES);
                    break;

                case (R.id.btn_coin3):
                    presenter.insertCoin(AcceptedCoins.QUARTERS);
                    break;
            }
        }
    };

    View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case (R.id.btn_item1):
                    presenter.selectItemId(VendingUtilts.ITEM_1_ID);
                    break;

                case (R.id.btn_item2):
                    presenter.selectItemId(VendingUtilts.ITEM_2_ID);
                    break;

                case (R.id.btn_item3):
                    presenter.selectItemId(VendingUtilts.ITEM_3_ID);
                    break;

            }
        }
    };

    View.OnClickListener onAbortClickedListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            presenter.abortPurchasing();
        }
    };

    View.OnClickListener onConfirmClickedListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            presenter.confirmPurchasing();
        }
    };

    View.OnClickListener onResetClickedListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            resetPurchase();
        }
    };

    /**
     * Pop up a dialog box to show transaction summaries.
     */
    View.OnClickListener onSummaryClickedListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            List<PurchaseHistory> histories = presenter.getHistories();
            Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.summary_dialog);
            ListView summaryList = (ListView) dialog.findViewById(R.id.summaryList);
            SummaryAdapter adapter = new SummaryAdapter(getActivity(), R.layout.adapter_summary);
            summaryList.setAdapter(adapter);
            adapter.setHistoryList(histories);
            dialog.show();
        }
    };

    //-----------  Listener defines finished --------

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vending_machine, container, false);
        initialPresenter();
        initialView(view);
        return view;
    }

    private void initialPresenter(){
        presenter = new VendingMachineFragPresenter();
        presenter.setPanel(this);
    }

    private void initialView(View view){
        item1Counter = (TextView) view.findViewById(R.id.item1_count);
        item2Counter = (TextView) view.findViewById(R.id.item2_count);
        item3Counter = (TextView) view.findViewById(R.id.item3_count);
        nickelBtn = (Button) view.findViewById(R.id.btn_coin1);
        dimeBtn = (Button) view.findViewById(R.id.btn_coin2);
        quarterBtn = (Button) view.findViewById(R.id.btn_coin3);
        fundView = (TextView) view.findViewById(R.id.fund_text);
        item1Btn = (Button) view.findViewById(R.id.btn_item1);
        item2Btn = (Button) view.findViewById(R.id.btn_item2);
        item3Btn = (Button) view.findViewById(R.id.btn_item3);
        itemIdView = (TextView) view.findViewById(R.id.item_id_text);
        abortBtn = (Button) view.findViewById(R.id.btn_abort);
        confirmBtn = (Button) view.findViewById(R.id.btn_confirm);
        resetBtn = (Button) view.findViewById(R.id.btn_reset);
        returnFundsView = (TextView) view.findViewById(R.id.return_txt);
        itemImageView = (ImageView) view.findViewById(R.id.item_door);
        summarBtn = (Button) view.findViewById(R.id.btn_summary);
        presenter.getInventory();
        initialListener();
    }

    private void initialListener(){
        nickelBtn.setOnClickListener(onCoinClickListener);
        dimeBtn.setOnClickListener(onCoinClickListener);
        quarterBtn.setOnClickListener(onCoinClickListener);
        item1Btn.setOnClickListener(onItemClickListener);
        item2Btn.setOnClickListener(onItemClickListener);
        item3Btn.setOnClickListener(onItemClickListener);
        abortBtn.setOnClickListener(onAbortClickedListener);
        confirmBtn.setOnClickListener(onConfirmClickedListener);
        resetBtn.setOnClickListener(onResetClickedListener);
        summarBtn.setOnClickListener(onSummaryClickedListener);
    }

    /**
     * Update inventory info to the viewlayer
     * @param itemId
     * @param count
     */
    @Override
    public void renderItemInventory(String itemId, int count) {
        if (itemId.equals(VendingUtilts.ITEM_1_ID))
            item1Counter.setText(count + "");
        if (itemId.equals(VendingUtilts.ITEM_2_ID))
            item2Counter.setText(count + "");
        if (itemId.equals(VendingUtilts.ITEM_3_ID))
            item3Counter.setText(count + "");
    }

    /**
     * Reset view when purchase is finished
     */
    @Override
    public void clearViews() {
        fundView.setText("");
        itemIdView.setText("");
    }

    /**
     * Update funds info while the user is inserting coins into slot
     * @param funds
     */
    @Override
    public void updateAvailableFundsView(int funds) {
        fundView.setText(funds + "c");
    }

    /**
     * Upadate item selection view when the user selects items
     * @param itemId
     */
    @Override
    public void updateItemSelectView(String itemId) {
        itemIdView.setText(itemId);
    }

    /**
     * Update funds return view when the machine returns changes or the purchasing been aborted.
     * @param funds
     */
    @Override
    public void updateReturnFundsView(int funds) {
        if (funds == 0) returnFundsView.setText("");
        returnFundsView.setText(funds + "c");
        clearViews();
    }

    /**
     * Toast alert in two cases:
     * 1. No sufficient funds. The inserted coins will be returned
     * 2. The selected item was sold out. The inserted coins will be returned
     * @param msg
     */
    @Override
    public void displayAlert(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
    }


    /**
     * Display an item image indicates teh selected item was delivered successfully.
     * @param itemId
     */
    @Override
    public void kickOutItem(String itemId) {
        if (itemId.equals(VendingUtilts.ITEM_1_ID)) {
            itemImageView.setImageResource(R.mipmap.coffee1);
        } else if (itemId.equals(VendingUtilts.ITEM_2_ID)) {
            itemImageView.setImageResource(R.mipmap.coffee2);
        } else if (itemId.equals(VendingUtilts.ITEM_3_ID)) {
            itemImageView.setImageResource(R.mipmap.coffee3);
        }
    }

    /**
     * Once the Reset button pressed. reset machine to ready state
     */
    private void resetPurchase(){
        fundView.setText("");
        itemIdView.setText("");
        returnFundsView.setText("");
        itemImageView.setImageResource(R.mipmap.closed_door);
    }

}

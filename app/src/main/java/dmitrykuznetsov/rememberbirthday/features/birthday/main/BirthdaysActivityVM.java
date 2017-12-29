package dmitrykuznetsov.rememberbirthday.features.birthday.main;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dmitrykuznetsov.rememberbirthday.BR;
import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.common.adapter.RecyclerBindingAdapter;
import dmitrykuznetsov.rememberbirthday.common.adapter.RecyclerConfiguration;
import dmitrykuznetsov.rememberbirthday.common.base.AbstractListActivityVM;
import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import dmitrykuznetsov.rememberbirthday.common.support.Constants;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.AddPersonActivity;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.interactor.BirthdaysInteractor;

import static android.app.Activity.RESULT_OK;

/**
 * Created by dmitry on 12.05.17.
 */

public class BirthdaysActivityVM extends AbstractListActivityVM<BirthdaysActivity, PersonData> {

    private List<PersonData> users = new ArrayList<>();
    private BirthdaysInteractor birthdaysInteractor;

    public BirthdaysActivityVM(BirthdaysActivity activity, RecyclerConfiguration configuration, BirthdaysInteractor birthdaysInteractor) {
        super(activity, configuration);
        this.birthdaysInteractor = birthdaysInteractor;

        refreshData();
    }

    private void refreshData() {
        users = birthdaysInteractor.getPersonDataList();
        adapter.setItems(users);
    }

    @Override
    protected void onRecyclerItemClick(PersonData item) {
        Toast.makeText(getActivity(), String.valueOf(item.getId()), Toast.LENGTH_LONG).show();
    }

    @Override
    protected List<PersonData> getAdapterList() {
        return users;
    }

    @Override
    protected int getVariable() {
        return BR.person;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_brithday;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

//        SearchManager searchManager = (SearchManager) getSystemService((Context.SEARCH_SERVICE));
//        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//
//        int imageId = getResources().getIdentifier("android:id/search_button", null, null);

//        ImageView imageView = (ImageView) searchView.findViewById(imageId);
//        imageView.setBackgroundResource(R.drawable.search);
//
//
//        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
//        View searchPlate = searchView.findViewById(searchPlateId);
//        searchPlate.setBackgroundResource(R.drawable.textfield_searchview_holo_light);

    }

    @Override
    public void onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_name:
                AddPersonActivity.open(getActivity(), Constants.RESULT_ADD_PERSON);
                break;
            case R.id.action_search:
                //item.setShowAsAction(item.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
                break;
            case R.id.action_settings:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.RESULT_ADD_PERSON) {
                refreshData();
            }
        }
    }

}
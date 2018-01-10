package dmitrykuznetsov.rememberbirthday.features.birthday.main;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.PopupMenu;

import java.util.List;

import dmitrykuznetsov.rememberbirthday.BR;
import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.common.adapter.RecyclerBindingAdapter;
import dmitrykuznetsov.rememberbirthday.common.adapter.RecyclerConfiguration;
import dmitrykuznetsov.rememberbirthday.common.base.AbstractListActivityVM;
import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import dmitrykuznetsov.rememberbirthday.common.support.Constants;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.AddPersonActivity;
import dmitrykuznetsov.rememberbirthday.features.birthday.detail.DetailBirthdayActivity;
import dmitrykuznetsov.rememberbirthday.features.birthday.edit.EditPersonActivity;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.interactor.BirthdaysInteractor;
import dmitrykuznetsov.rememberbirthday.features.birthday.main.model.PersonItemView;
import io.reactivex.disposables.CompositeDisposable;

import static android.app.Activity.RESULT_OK;

/**
 * Created by dmitry on 12.05.17.
 */

public class BirthdaysActivityVM extends AbstractListActivityVM<BirthdaysActivity, PersonItemView> {

    private CompositeDisposable disposables = new CompositeDisposable();
    private List<PersonItemView> persons;
    private BirthdaysInteractor birthdaysInteractor;

    public BirthdaysActivityVM(BirthdaysActivity activity, List<PersonItemView> persons, RecyclerConfiguration configuration, BirthdaysInteractor birthdaysInteractor) {
        super(activity, persons, configuration);
        this.birthdaysInteractor = birthdaysInteractor;
        this.persons = persons;
//        refreshData();
    }

    private void refreshData() {
        isLoading.set(true);
        disposables.add(birthdaysInteractor.getPersons()
                .subscribe(this::onSuccess, this::onError));
    }

    private void onSuccess(List<PersonItemView> persons) {
        isLoading.set(false);
        this.persons.clear();
        this.persons.addAll(persons);
        adapter.notifyDataSetChanged();
    }

    private void onError(Throwable throwable) {
        isLoading.set(false);
        errorMessage.set(activity.getString(R.string.error_unknown));
    }

    @Override
    protected void onRecyclerItemClick(PersonItemView personItemView) {
        PersonData personData = personItemView.getPersonData();
        DetailBirthdayActivity.open(activity, personData);
    }

    @Override
    protected void onRecyclerLongItemClick(PersonItemView personItemView, RecyclerBindingAdapter.BindingHolder holder) {
        PopupMenu popup = new PopupMenu(getActivity(), holder.itemView);
        popup.inflate(R.menu.options_menu);
        popup.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.menuEditPerson:
                    Intent intent = new Intent(activity, EditPersonActivity.class);
                    PersonData personData = personItemView.getPersonData();
                    intent.putExtra(Constants.PERSON_DATA, personData);
                    activity.startActivityForResult(intent, Constants.RESULT_ADD_PERSON);
                    break;
                case R.id.menuDeletePerson:
                    //handle menu2 click
                    break;
            }
            return false;
        });
        popup.show();
    }

    @Override
    protected List<PersonItemView> getAdapterList() {
        return persons;
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

    @Override
    public void onStart() {
        super.onStart();
        refreshData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.dispose();
    }
}
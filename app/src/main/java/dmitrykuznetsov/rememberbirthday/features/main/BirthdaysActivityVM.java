package dmitrykuznetsov.rememberbirthday.features.main;

import android.content.Intent;
import android.databinding.ObservableBoolean;

import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;


import java.util.List;

import dmitrykuznetsov.rememberbirthday.BR;
import dmitrykuznetsov.rememberbirthday.R;
import dmitrykuznetsov.rememberbirthday.common.adapter.RecyclerConfiguration;
import dmitrykuznetsov.rememberbirthday.common.base.AbstractListActivityVM;
import dmitrykuznetsov.rememberbirthday.common.data.model.PersonData;
import dmitrykuznetsov.rememberbirthday.common.support.Constants;
import dmitrykuznetsov.rememberbirthday.features.birthday.add.AddPersonActivity;
import dmitrykuznetsov.rememberbirthday.features.birthday.detail.DetailBirthdayActivity;
import dmitrykuznetsov.rememberbirthday.features.main.interactor.BirthdaysInteractor;
import dmitrykuznetsov.rememberbirthday.features.main.model.PersonItemView;
import dmitrykuznetsov.rememberbirthday.features.settings.SettingsActivity;
import io.reactivex.disposables.CompositeDisposable;

import static android.app.Activity.RESULT_OK;

/**
 * Created by dmitry on 12.05.17.
 */

public class BirthdaysActivityVM extends AbstractListActivityVM<BirthdaysActivity, PersonItemView> {

    public ObservableBoolean isBirthdays = new ObservableBoolean(false);

    private CompositeDisposable disposables = new CompositeDisposable();
    private List<PersonItemView> persons;
    private BirthdaysInteractor birthdaysInteractor;

    private boolean isSearchNow;
    private String searchText;

    public BirthdaysActivityVM(BirthdaysActivity activity, List<PersonItemView> persons, RecyclerConfiguration configuration, BirthdaysInteractor birthdaysInteractor) {
        super(activity, persons, configuration);
        this.birthdaysInteractor = birthdaysInteractor;
        this.persons = persons;
//        getPersons();
    }

    @Override
    protected void onRecyclerItemClick(PersonItemView personItemView) {
        PersonData personData = personItemView.getPersonData();
        DetailBirthdayActivity.open(activity, personData);
    }

    @Override
    protected int getVariable() {
        return BR.person;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_brithday;
    }

    private void getPersons() {
        isLoading.set(true);
        disposables.add(birthdaysInteractor.getPersons()
                .subscribe(this::onSuccess, this::onError));
    }

    private void getPersonsByName(String searchName) {
        isLoading.set(true);
        disposables.add(birthdaysInteractor.getPersonsByName(searchName)
                .subscribe(this::onSuccess, this::onError));
    }

    private void onSuccess(List<PersonItemView> persons) {
        isLoading.set(false);
        if (persons.size() > 0) {
            isBirthdays.set(true);
        } else {
            if (!isSearchNow) {
                isBirthdays.set(false);
            }
        }
        this.persons.clear();
        this.persons.addAll(persons);
        adapter.setItems(this.persons);
    }

    private void onError(Throwable throwable) {
        isLoading.set(false);
        errorMessage.set(activity.getString(R.string.error_unknown));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        MenuItem addItem = menu.findItem(R.id.action_add_name);

        final SearchView searchView = (SearchView) searchViewItem.getActionView();
        EditText searchEditText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setHintTextColor(activity.getResources().getColor(R.color.colorSearch));

        searchViewItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                isSearchNow = true;
                addItem.setVisible(false);
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                isSearchNow = false;
                addItem.setVisible(true);
                activity.invalidateOptionsMenu();
                searchText = null;
                getPersons();
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String searchName) {
                if (!(searchName.equals("") && searchText == null)) {
                    getPersonsByName(searchName);
                }
                searchText = searchName;
                return false;
            }
        });
    }

    public void addPerson(){
        AddPersonActivity.open(getActivity(), Constants.RESULT_ADD_PERSON);
    }

    @Override
    public void onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_name:
                addPerson();
                break;
            case R.id.action_search:
//                item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
                break;
            case R.id.action_settings:
                Intent intent = new Intent(getActivity(), SettingsActivity.class);
                activity.startActivity(intent);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.RESULT_ADD_PERSON) {
                getPersons();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (isSearchNow) {
            getPersonsByName(searchText);
        } else {
            getPersons();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.dispose();
    }
}
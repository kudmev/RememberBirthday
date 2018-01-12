package dmitrykuznetsov.rememberbirthday.features.birthday.main;

import android.content.Intent;
import android.databinding.ObservableBoolean;

import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
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
import dmitrykuznetsov.rememberbirthday.features.birthday.settings.SettingsActivity;
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
        refreshData("");
    }

    private void refreshData(String searchText) {
        isLoading.set(true);
        disposables.add(birthdaysInteractor.getPersons(searchText)
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
                BirthdaysActivityVM.this.searchText = null;
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String searchText) {
                if (!(searchText.equals("") && BirthdaysActivityVM.this.searchText == null)) {
                    refreshData(searchText);
//                    List<PersonItemView> searchablePersons = new ArrayList<>();
//                    for (PersonItemView personItemView : persons) {
//                        String personName = personItemView.getPersonData().getName().toLowerCase();
//                        String searchName = searchText.toLowerCase();
//                        if (personName.contains(searchName)) {
//                            searchablePersons.add(personItemView);
//                        }
//                    }
//                    adapter.setItems(searchablePersons);
                }
                BirthdaysActivityVM.this.searchText = searchText;

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
                refreshData("");
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!isSearchNow) {
            refreshData("");
        } else {
            refreshData(searchText);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposables.dispose();
    }
}
package com.example.pinayflix.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.pinayflix.local.dao.SavedItemDao;
import com.example.pinayflix.local.database.AppDatabase;
import com.example.pinayflix.model.datamodel.SavedItem;

import java.util.List;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SavedItemRepository {
    private AppDatabase db;
    private SavedItemDao savedItemDao;
    private static final String TAG = "SavedItemRepository";
    private MutableLiveData<List<SavedItem>> saveItemsLiveData;
    private MutableLiveData<Boolean> savedItemExists = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public SavedItemRepository(Application application) {
        db = Room.databaseBuilder(application,AppDatabase.class,"saved_items_database.db").build();
        this.savedItemDao = db.savedItemDao();
        saveItemsLiveData = new MutableLiveData<>();
        Log.d(TAG, "SavedItemRepository: Created");
    }
    public void insertSavedItem(SavedItem savedItem){
     Disposable disposable =  Completable.fromRunnable(() ->{
            savedItemDao.insert(savedItem);
        }).subscribeOn(Schedulers.io()).subscribe(()->{
            Log.d(TAG, "insertSavedItem: " + savedItem.getName());
        });
     compositeDisposable.add(disposable);
    }
    public void deleteSavedItem(SavedItem savedItem){
       Disposable disposable = Completable.fromRunnable(()->{
           savedItemDao.delete(savedItem.getId());
       }).subscribeOn(Schedulers.io()).subscribe(()->{
           Log.d(TAG, "deleteSavedItem: Name " + savedItem.getName());

       });
        compositeDisposable.add(disposable);

    }
    public void checkIfSavedItemExists(int id){
        Log.d(TAG, "checkIfSavedItemExists:Checking ");
        savedItemDao.checkIfExists(id).subscribeOn(Schedulers.io())
        .observeOn(Schedulers.computation()).subscribe(new SingleObserver<Boolean>() {
            @Override
            public void onSubscribe(io.reactivex.rxjava3.disposables.@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onSuccess(@NonNull Boolean aBoolean) {
                Log.d(TAG, "onSuccess: " + aBoolean);
            savedItemExists.postValue(aBoolean);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });
    }
    public void getAllSavedItem(){
        saveItemsLiveData.postValue(savedItemDao.getAll());
        Log.d(TAG, "getAllSavedItem: Getting All Saved Item");


    }

    public LiveData<List<SavedItem>> getSaveItemsLiveData() {
        return saveItemsLiveData;
    }

    public LiveData<Boolean> getSavedItemExists() {
        return savedItemExists;
    }

    public void disposeSubscribers(){
        compositeDisposable.dispose();
    }
}

package com.pandey.todos.data;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class DbHelper {

    private AppDatabase appDatabase;

    private static final String TAG = "DbHelper";

//    private static <T> Observable<T> makeObservable(final OnList<TaskEntry> taskEntries) {
//        return Observable.create(
//                new ObservableOnSubscribe<T>() {
//                    @Override
//                    public void subscribe(ObservableEmitter<T> emitter) throws Exception {
//                        try {
//                            emitter.onNext((T) taskEntries);
//                        }catch (Exception e){
//                            Log.e(TAG, "Error reading from the database", e);
//                        }
//                    }
//                });
//    }

    public Observable<List<TaskEntry>> getAllTasks() {
        return appDatabase.taskDao().loadAllTasks()
                .subscribeOn(Schedulers.io());
    }
}

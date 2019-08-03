//package com.pandey.popcorn4.rxexample;
//
//import com.pandey.popcorn4.rxexample.data.Cities;
//import com.pandey.popcorn4.rxexample.data.City;
//
//import io.reactivex.Observable;
//import retrofit2.Retrofit;
//import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
//
//public class MeetUpPresenter {
//
//    double warsawLat = 52.229841;
//    double warsawLon = 21.011736;
//
//    // Getting cities nearby the give lat-long
//    Retrofit retrofit = new Retrofit.Builder()
//            .baseUrl("https://api.meetup.com/")
//            .addCallAdapterFactory(
//                    RxJava2CallAdapterFactory.create())
//            .addConverterFactory(
//                    JacksonConverterFactory.create(objectMapper))
//            .build();
//
//    MeetUpApiService meetup = retrofit.create(MeetUpApiService.class);
//
//    Observable<Cities> cities = meetup.listCities(warsawLat, warsawLon);
//    Observable<> cityObs = cities
//            .concatMapIterable(Cities::getResults);
//    Observable<String> map = cityObs
//            .filter(city -> city.distanceTo(warsawLat, warsawLon) < 50)
//            .map(Cities.City::getCity);
//
//
//    // Getting population of the cities
//    GeoNames geoNames = new Retrofit.Builder()
//            .baseUrl("http://api.geonames.org")
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
//            .build()
//            .create(GeoNames.class);
//
//
//    Observable<Long> totalPopulation = meetup
//            .listCities(warsawLat, warsawLon)
//            .concatMapIterable(Cities::getResults)
//            .filter(city -> city.distanceTo(warsawLat, warsawLon) < 50)
//            .map(City::getCity)
//            .flatMap(geoNames::populationOf)
//            .reduce(0L, (x, y) -> x + y);
//
//
//    default Observable<Integer> populationOf(String query) {
//        return search(query)
//                .concatMapIterable(SearchResult::getGeonames)
//                .map(Geoname::getPopulation)
//                .filter(p -> p != null)
//                .singleOrDefault(0)
//                .doOnError(th ->
//                        log.warn("Falling back to 0 for {}", query, th))
//                .onErrorReturn(th -> 0)
//                .subscribeOn(Schedulers.io());
//    }
//
//    default Observable<SearchResult> search(String query) {
//        return search(query, 1, "LONG", "some_user");
//    }
//}

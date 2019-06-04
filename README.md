# ProRecyclerView

## Description
Customized Android RecyclerView to make easier to use.
Features:
* Lottie animation while any error to load data with customized message and button handling
* Lottie animation if adapter is empty
* [Shimmer loading](https://facebook.github.io/shimmer-android/ "Shimmer effect for Android") while loading the data
* Swipe to refresh
* Load more items when you reach the last

## Integration
Clone the repository and add to your project as module
> File -> New -> **Import Module**

## Usage
* Use directly ProRecyclerView:
``` xml
  <com.smartersstudio.prorecyclerview.MyRecyclerView
    android:layout_width="match_parent"
    android:id="@+id/container"
    android:layout_height="match_parent"/>
```
* Supported Attributes:
``` xml
  <attr name="empty_asset" format="string" />
  <attr name="error_asset" format="string"/>
  <attr name="empty_text" format="string"/>
  <attr name="error_text" format="string"/>
  <attr format="reference" name="mainLayoutId"/>
```
## Java Usage
<img align="right" alt="Error Demo" src="https://github.com/SmarttersStudio/ProRecyclerView/blob/master/screenshots/Error.gif" width="200" />
<br>

* Error:
``` java
  recyclerView.setErrorMessage("No internet");
  recyclerView.setErrorMessage(getResources().getString(R.string.no_internet_connection));
  recyclerView.setErrorAsset("error.json");       //Lottie asset name
  recyclerView.showError();                       //Visible error layout to user
  recyclerView.onRetry(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      //Do retry
      Toast.makeText(MainActivity.this, "Retrying", Toast.LENGTH_SHORT).show();
    }
 });  
```

<br>
<img align="right" alt="Empty Demo" src="https://github.com/SmarttersStudio/ProRecyclerView/blob/master/screenshots/No%20data.gif" width="200" />

* Empty
``` java
	recyclerView.setEmptyMessage("No data found");
	recyclerView.setEmptyMessage(getResources().getString(R.string.no_data_found));
	recyclerView.setEmptyAsset("empty.json");       //Lottie asset name
	recyclerView.showEmpty();
	recyclerView.onReload(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			//Do reload
			Toast.makeText(MainActivity.this, "Reloading", Toast.LENGTH_SHORT).show();
	}
});
     
```

<br>

<img align="right" alt="Loading Demo" src="https://github.com/SmarttersStudio/ProRecyclerView/blob/master/screenshots/Loading.gif" width="200" />
<br><br><br>

* Loading
``` java
  recyclerView.addLoaderLayout(R.layout.shimmer_loader);    //shimmer loading layout
  recyclerView.showLoader();
```

<br><br><br><br><br><br>

<img align="right" alt="Refresh demo" src="https://github.com/SmarttersStudio/ProRecyclerView/blob/master/screenshots/Refresh.gif" width="200" />
<br><br>

* On Refresh
``` java
  recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
    @Override
    public void onRefresh() {
      //Do refresh
      recyclerView.setRefreshing(false);  //Remove refreshing progress
    }
  });
```
<br><br><br>

* On More
``` java
  recyclerView.setOnMoreListener(new OnMoreListener() {
    @Override
    public void onMoreAsked(int overallItemsCount, int maxLastVisiblePosition) {
      //Fetch data
    }
  });
```

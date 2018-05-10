# ActivityResultManager
convert `activity.startActivityForResult` to callback


### Usage
``` kotlin
ActivityResultManager(activity).startActivityForResult(intent) { resultCode, data ->
    // handle data...
}
```

### thanks

[RxPermissions](https://github.com/tbruyelle/RxPermissions)

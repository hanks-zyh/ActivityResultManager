# ActivityResultManager
convert `activity.startForResult` to callback


### Usage
``` kotlin
ActivityResultManager(activity).startForResult(intent) { resultCode, data ->
    // handle data...
}
```

### thanks

[RxPermissions](https://github.com/tbruyelle/RxPermissions)

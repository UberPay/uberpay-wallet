Uberpay Wallet
===============

Our goal is to develop and consumer friendly multicoin wallet that will support the Uberpay Bitcoin Kiosks. Store all the best cryptocurrency through a single app, without sacrificing security. Private keys are stored on your own device. Instead of having one backup file for every coin, you get a master key that can be memorized or stored on a piece of paper. Restore all wallets from a single recovery phrase.


## Contributions

Your contributions are very welcome, be it translations, extra features or new coins support. Just
fork this repo and create a pull request with your changes.

For new coins support read this [document](https://coinomi.com/AddingSupportForANewCurrency/).
Generally you need:

* Electrum-server support
* Coinomi core support
* A beautiful vector icon
* Entry to the [BIP44 registry](https://github.com/satoshilabs/docs/blob/master/slips/slip-0044.rst) that is maintained by Satoshi labs


## Releasing the app

To release the app follow the steps.

1) Change the following:

* in strings.xml app_name string to "Uberpay" and app_package to com.mygeopay.wallet
* in build.gradle the package from "com.mygeopay.wallet.dev" to "com.mygeopay.wallet"
* in AndroidManifest.xml the android:icon to "ic_launcher" and all "com.mygeopay.wallet.dev.*"  to "com.mygeopay.wallet.*"
* remove all ic_launcher_dev icons with `rm wallet/src/main/res/drawable*/ic_launcher_dev.png`
* setup ACRA and ShapeShift

2) Then in the Android Studio go to:

* Build -> Clean Project and without waiting...
* Build -> Generate Signed APK and generate a signed APK. ... and now you can grab yourself a nice cup of tea.

3) Test this APK (TODO: with instrumentation tests).

For now test it manually by installing it `adb install -r wallet/wallet-release.apk`

> This one is in the TODOs and must be automated
> because it will be here that you take a break ;)

4) Upload to Play Store and check for any errors and if all OK publish in beta first.

5) Create a GIT release commit:

* Create a commit with the log entry similar to the description in the Play Store
* Create a tag with the version of the released APK with `git tag vX.Y.Z <commit-hash>`


## Version history

New in version 1.6.01
- Can set an amount in receive screen
- Chinese and Japanese translations
- Updated the recovery phrase creation procedure

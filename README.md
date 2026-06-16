# @capgo/capacitor-verisoul

<a href="https://capgo.app/"><img src="https://capgo.app/readme-banner.svg?repo=Cap-go/capacitor-verisoul" alt="Capgo - Instant updates for Capacitor" /></a>

<div align="center">
  <h2><a href="https://capgo.app/?ref=plugin_verisoul"> Get Instant updates for your App with Capgo</a></h2>
  <h2><a href="https://capgo.app/consulting/?ref=plugin_verisoul"> Missing a feature? We'll build the plugin for you</a></h2>
</div>

Capacitor plugin for Verisoul native fraud-prevention sessions.

## Installation

You can use our AI-Assisted Setup to install the plugin. Add the Capgo skills to your AI tool using the following command:

```bash
npx skills add https://github.com/cap-go/capacitor-skills --skill capacitor-plugins
```

Then use the following prompt:

```text
Use the `capacitor-plugins` skill from `cap-go/capacitor-skills` to install the `@capgo/capacitor-verisoul` plugin in my project.
```

If you prefer Manual Setup, install the plugin by running the following commands and follow the platform-specific instructions below:

```bash
npm install @capgo/capacitor-verisoul
npx cap sync
```

## iOS setup

Verisoul recommends enabling App Attest for stronger iOS device checks. Add the capability in Xcode and include the entitlement that matches your environment:

```xml
<key>com.apple.developer.devicecheck.appattest-environment</key>
<string>production</string>
```

Use `development` for test builds.

## Android setup

The plugin adds the Verisoul Maven repository and required network permissions. If your app build cannot resolve `ai.verisoul:android`, add this repository to your app root Gradle repositories:

```groovy
maven { url = uri("https://us-central1-maven.pkg.dev/verisoul/android") }
```

## Usage

```ts
import { Verisoul } from '@capgo/capacitor-verisoul';

await Verisoul.configure({
  environment: 'prod',
  projectId: 'YOUR_PROJECT_ID',
});

const { sessionId } = await Verisoul.getSessionId();

await Verisoul.reinitialize();
```

On Android, forward touch events when you need Verisoul touch-signal collection:

```ts
await Verisoul.recordTouchEvent({
  x: 120,
  y: 240,
  action: 'down',
});
```

## API

<docgen-index>

* [`configure(...)`](#configure)
* [`getSessionId()`](#getsessionid)
* [`reinitialize()`](#reinitialize)
* [`recordTouchEvent(...)`](#recordtouchevent)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### configure(...)

```typescript
configure(options: ConfigureOptions) => Promise<void>
```

Initialize Verisoul. Call this once, early in app startup.

| Param         | Type                                                          |
| ------------- | ------------------------------------------------------------- |
| **`options`** | <code><a href="#configureoptions">ConfigureOptions</a></code> |

--------------------


### getSessionId()

```typescript
getSessionId() => Promise<SessionIdResult>
```

Return the current Verisoul session identifier.

The value may take a few seconds to become available after `configure()`.

**Returns:** <code>Promise&lt;<a href="#sessionidresult">SessionIdResult</a>&gt;</code>

--------------------


### reinitialize()

```typescript
reinitialize() => Promise<void>
```

Reset Verisoul signal collection and create a fresh session.

Use this when account context changes, for example after logout/login.

--------------------


### recordTouchEvent(...)

```typescript
recordTouchEvent(options: RecordTouchEventOptions) => Promise<void>
```

Forward a touch event to Verisoul.

Android uses this for bot-detection touch signals. iOS resolves without
work because the iOS SDK does not expose a matching public API.

| Param         | Type                                                                        |
| ------------- | --------------------------------------------------------------------------- |
| **`options`** | <code><a href="#recordtoucheventoptions">RecordTouchEventOptions</a></code> |

--------------------


### Interfaces


#### ConfigureOptions

| Prop              | Type                                                                | Description                                                                                                |
| ----------------- | ------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------- |
| **`environment`** | <code><a href="#verisoulenvironment">VerisoulEnvironment</a></code> | Verisoul project environment. Use `prod` or `production` for production traffic and `sandbox` for testing. |
| **`projectId`**   | <code>string</code>                                                 | Verisoul project identifier.                                                                               |


#### SessionIdResult

| Prop            | Type                | Description                                                                                                         |
| --------------- | ------------------- | ------------------------------------------------------------------------------------------------------------------- |
| **`sessionId`** | <code>string</code> | Verisoul session identifier. Send this value to your backend before calling Verisoul's server-side assessment APIs. |


#### RecordTouchEventOptions

| Prop         | Type                                                                | Description                                                                                                                                                                      |
| ------------ | ------------------------------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`x`**      | <code>number</code>                                                 | Touch X coordinate in view pixels.                                                                                                                                               |
| **`y`**      | <code>number</code>                                                 | Touch Y coordinate in view pixels.                                                                                                                                               |
| **`action`** | <code><a href="#verisoultouchaction">VerisoulTouchAction</a></code> | Touch action. Android forwards this to the native Verisoul SDK. iOS currently ignores this method because the Verisoul iOS SDK does not expose the same public touch-event hook. |


### Type Aliases


#### VerisoulEnvironment

<code>'dev' | 'sandbox' | 'staging' | 'prod' | 'production'</code>


#### VerisoulTouchAction

<code>'down' | 'up' | 'move' | 0 | 1 | 2</code>

</docgen-api>

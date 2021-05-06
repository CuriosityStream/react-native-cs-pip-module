# react-native-cs-pip-module

PictureInPicture support

## Installation

```sh
npm install react-native-cs-pip-module
```

```sh
yarn add react-native-cs-pip-module
```

## Usage

### PiP availability (iOS + Android)
```js
import CsPipModule from "react-native-cs-pip-module";
// ...
const result = CsPipModule.isPiPSupported();
```

### PiP run (Android only)
```js
const result = CsPipModule.enterPiPMode();
```

## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

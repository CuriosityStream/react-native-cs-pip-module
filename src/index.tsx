import { NativeModules } from 'react-native';

type CsPipModuleType = {
  multiply(a: number, b: number): Promise<number>;
};

const { CsPipModule } = NativeModules;

export default CsPipModule as CsPipModuleType;

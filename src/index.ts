import { registerPlugin } from '@capacitor/core';

import type { VerisoulPlugin } from './definitions';

const Verisoul = registerPlugin<VerisoulPlugin>('Verisoul', {
  web: () => import('./web').then((m) => new m.VerisoulWeb()),
});

export * from './definitions';
export { Verisoul };

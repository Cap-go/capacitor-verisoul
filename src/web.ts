import { WebPlugin } from '@capacitor/core';

import type { ConfigureOptions, RecordTouchEventOptions, SessionIdResult, VerisoulPlugin } from './definitions';

export class VerisoulWeb extends WebPlugin implements VerisoulPlugin {
  async configure(_options: ConfigureOptions): Promise<void> {
    void _options;
    throw this.unimplemented('Verisoul native SDK is not available on web.');
  }

  async getSessionId(): Promise<SessionIdResult> {
    throw this.unimplemented('Verisoul native SDK is not available on web.');
  }

  async reinitialize(): Promise<void> {
    throw this.unimplemented('Verisoul native SDK is not available on web.');
  }

  async recordTouchEvent(_options: RecordTouchEventOptions): Promise<void> {
    void _options;
    throw this.unimplemented('Verisoul native SDK is not available on web.');
  }
}

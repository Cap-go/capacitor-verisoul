export type VerisoulEnvironment = 'dev' | 'sandbox' | 'staging' | 'prod' | 'production';

export type VerisoulTouchAction = 'down' | 'up' | 'move' | 0 | 1 | 2;

export interface ConfigureOptions {
  /**
   * Verisoul project environment.
   *
   * Use `prod` or `production` for production traffic and `sandbox` for testing.
   */
  environment: VerisoulEnvironment;

  /**
   * Verisoul project identifier.
   */
  projectId: string;
}

export interface SessionIdResult {
  /**
   * Verisoul session identifier. Send this value to your backend before calling
   * Verisoul's server-side assessment APIs.
   */
  sessionId: string;
}

export interface RecordTouchEventOptions {
  /**
   * Touch X coordinate in view pixels.
   */
  x: number;

  /**
   * Touch Y coordinate in view pixels.
   */
  y: number;

  /**
   * Touch action. Android forwards this to the native Verisoul SDK.
   *
   * iOS currently ignores this method because the Verisoul iOS SDK does not
   * expose the same public touch-event hook.
   */
  action: VerisoulTouchAction;
}

export interface VerisoulPlugin {
  /**
   * Initialize Verisoul. Call this once, early in app startup.
   */
  configure(options: ConfigureOptions): Promise<void>;

  /**
   * Return the current Verisoul session identifier.
   *
   * The value may take a few seconds to become available after `configure()`.
   */
  getSessionId(): Promise<SessionIdResult>;

  /**
   * Reset Verisoul signal collection and create a fresh session.
   *
   * Use this when account context changes, for example after logout/login.
   */
  reinitialize(): Promise<void>;

  /**
   * Forward a touch event to Verisoul.
   *
   * Android uses this for bot-detection touch signals. iOS resolves without
   * work because the iOS SDK does not expose a matching public API.
   */
  recordTouchEvent(options: RecordTouchEventOptions): Promise<void>;
}

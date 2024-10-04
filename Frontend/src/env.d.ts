interface ImportMetaEnv extends Readonly<Record<string, any>> {
  readonly VITE_APP_API_URL: string;
  readonly VITE_BE_API_URL: string;
  readonly VITE_NODE_ENV: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}

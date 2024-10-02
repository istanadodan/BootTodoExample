interface ImportMetaEnv extends Readonly<Record<string, any>> {
  readonly VITE_APP_API_URL: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}

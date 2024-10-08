import { defineConfig } from "vite";
import react from "@vitejs/plugin-react";
import tsconfigPaths from "vite-tsconfig-paths";
import EnvironmentPlugin from "vite-plugin-environment";

export default defineConfig({
  plugins: [
    react(),
    tsconfigPaths(),
    EnvironmentPlugin("all", { prefix: "VITE_" }),
  ],
  build: {
    outDir: 'build',
  },
  resolve: {
    alias: [
      {
        find: "~antd",
        replacement: "antd",
      },
    ],
  },
});

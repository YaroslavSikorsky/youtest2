module.exports = {
    root: true,
    env: {
        node: true,
        browser: true,
        es2021: true
    },
    parser: "vue-eslint-parser",
    parserOptions: {
        parser: "@babel/eslint-parser", // для <script> в .vue
        requireConfigFile: false,        // ключевой момент — ESLint не ищет Babel config
        ecmaVersion: 2021,
        sourceType: "module"
    },
    extends: [
        "plugin:vue/vue3-recommended",
        "eslint:recommended"
    ],
    rules: {
        // Добавь свои правила ESLint здесь
        "no-console": process.env.NODE_ENV === "production" ? "warn" : "off",
        "no-debugger": process.env.NODE_ENV === "production" ? "warn" : "off"
    }
};

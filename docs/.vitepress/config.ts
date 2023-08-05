import {defineConfig} from 'vitepress'

// https://vitepress.dev/reference/site-config
export default defineConfig({
    title: "Lazyegg Framework",
    description: "docs for lazyegg framework",
    lastUpdated: true,
    themeConfig: {
        // https://vitepress.dev/reference/default-theme-config
        nav: [
            {text: 'Home', link: '/'},
            {text: 'Examples', link: '/markdown-examples'}
        ],
        sidebar: [
            {
                text: '导言',
                collapsed: false,
                items: [
                    {text: 'What is ?', link: '/introduction/index'}
                ]
            }, {
                text: 'Examples',
                collapsed: false,
                items: [
                    {text: '开发流程', link: '/dev-flow'},
                    {text: 'Markdown Examples', link: '/markdown-examples'},
                    {text: 'Runtime API Examples', link: '/api-examples'}
                ]
            }, {
                text: 'Api Reference',
                collapsed: false,
                items: [
                    {text: '首页', link: '/api/index'}
                ]

            }
        ],
        socialLinks: [
            {icon: 'github', link: 'https://github.com/vuejs/vitepress'}
        ],
        editLink: {
            pattern: 'https://github.com/vuejs/vitepress/edit/main/docs/:path'
        },
        footer: {
            message: 'License.',
            copyright: 'Copyright © 2023-present LazyEgg'
        }
    }
})

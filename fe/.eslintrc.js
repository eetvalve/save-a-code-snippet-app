module.exports = {
  root: true,
  env: {
    node: true,
  },
  'extends': [
    'plugin:vue/strongly-recommended'
  ],
  rules: {
    'indent': ['error', 2],
    'vue/script-indent': [
      'error',
      2,
      { 'baseIndent': 1 }
    ],
  },
  'overrides': [
    {
      'files': ['*.vue'],
      'rules': {
        'indent': 'off'
      }
    }
  ]
};

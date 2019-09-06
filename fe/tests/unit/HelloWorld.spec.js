import {expect, assert} from 'chai';
import HelloWorld from '@/components/HelloWorld.vue';

describe('HelloWorld.vue', () => {
  it('renders props.msg when passed', () => {
      const test = 'test';
      assert('test' === test, 'test is true')
    });
});

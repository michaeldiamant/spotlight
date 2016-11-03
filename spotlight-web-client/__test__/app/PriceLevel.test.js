import React from 'react';
import renderer from 'react-test-renderer';
import PriceLevel from '../../src/app/PriceLevel';

test('price level renders price and size', () => {
  var rendered = renderer.create(
    <PriceLevel price='2.01' size='1' />
  );
  expect(rendered.toJSON()).toMatchSnapshot();
});


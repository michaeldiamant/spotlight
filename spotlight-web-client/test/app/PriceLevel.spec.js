import React from 'react';
import TestUtils from 'react-addons-test-utils';
import PriceLevel from '../../src/app/PriceLevel';
import Chai from 'chai';
import jsdom from 'jsdom-global';

describe('price level', () => {
  it('should display price and size', () => {
     jsdom();
     var Wrapper = React.createClass({
        render: function() {
          return (
            <div>{this.props.children}</div>
          );
        }
     });

     var c = TestUtils.renderIntoDocument(
       <Wrapper>
         <table>
            <tbody>
              <PriceLevel price='2.01' size='1' />
            </tbody>
         </table>
       </Wrapper>);
     var cols = TestUtils.scryRenderedDOMComponentsWithTag(c, 'td');
     Chai.expect(cols).to.have.length(2);
     Chai.expect(cols[0].innerHTML).to.equal('2.01');
     Chai.expect(cols[1].innerHTML).to.equal('1');
  });
});


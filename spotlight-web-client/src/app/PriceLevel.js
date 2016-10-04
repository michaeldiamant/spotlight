'use strict';

import React from 'react';

class PriceLevel extends React.Component {
  render() {
    return (
      <tr> 
        <td>{this.props.price}</td>
        <td>{this.props.size}</td>
      </tr>
    );
  }
};

export default PriceLevel;

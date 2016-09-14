var PriceLevel = React.createClass({
  render: function() {
    return (
      <tr> 
        <td>{this.props.price}</td>
        <td>{this.props.size}</td>
      </tr>
    );
  }
});

var BookSide = React.createClass({
  render: function() {
    var safeOrders = (this.props.orders ? this.props.orders : [])
    var renderedOrders = safeOrders.map(function(order) {
      return (
        <PriceLevel key={order.id} price={order.price} size="1" /> 
      );
    });
    return (
      <div>
        <h2>{this.props.name}</h2>
        <table>
          <tbody>
            {renderedOrders}
          </tbody>
        </table>
      </div>
    );
  }
});

var OrderBook = React.createClass({
  render: function() {
    return (
      <div>
        <h1>Order Book</h1>
          <div style={{display: 'inline-block'}}>
            <BookSide name="Bids" />
          </div>
          <div style={{display: 'inline-block'}}>
            <BookSide name="Offers" orders={this.props.offers} /> 
          </div>
      </div>
    );
  }
});

var OrderBookSimulator = React.createClass({
  getInitialState: function() {
    return {data: []};
  },
  onOfferSubmitted: function(offer) {
    this.state.data.push(offer)
    this.setState({data: this.state.data});    
  },
  render: function() {
    return (
      <div>
        <AddOfferForm onOfferSubmitted={this.onOfferSubmitted} />
        <OrderBook offers={this.state.data}/>
      </div>
    );
  }
});

var AddOfferForm = React.createClass({
  getInitialState: function(e) {
    return {price: ''};
  },
  onPriceChange: function(e) {
    this.setState({price: e.target.value});
  },
  handleSubmit: function(e) {
    e.preventDefault();
    var price = this.state.price.trim();
    if (!price) {
      return;
    }
    this.props.onOfferSubmitted({id: Date.now(), price: price});
  },
  render: function() {
    return (
      <form onSubmit={this.handleSubmit}>
        <p>Price: $<input type="text" onChange={this.onPriceChange} value={this.state.price}/></p>
        <input type="submit" value="Post" />
      </form>
    );
  }
});

ReactDOM.render(
  <OrderBookSimulator />,
  document.getElementById('content')
);

/**
 * Created by jutal on 16-11-8.
 */
/*var INFOS = [
 {date: '2016-11-8', content: 'content1'},
 {date: '2016-11-9', content: 'content2'}
 ];*/

/*整个App*/
var App = React.createClass({
  getInitialState: function () {
    return {
      INFOS: [{date: '2016-11-8', content: 'content1'},
        {date: '2016-11-9', content: 'content2'},
        {date: '2016-11-10', content: 'content3'}]
    };
  },

  handleContentSubmit: function (content) {
    var xmlhttp = new XMLHttpRequest();
    var DataGet;
    xmlhttp.onreadystatechange = function () {
      if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
        DataGet = xmlhttp.responseText;
        DataGet = eval(DataGet);
      }
    }
    content = encodeURI(encodeURI(content));
    xmlhttp.open("GET", "http://localhost:8080/addData?content=" + content, true);
    xmlhttp.send();
    this.setState({
      INFOS: DataGet
    });
  },

  componentDidMount: function () {
    var xmlhttp = new XMLHttpRequest();
    var DataGet;
    xmlhttp.onreadystatechange = function () {
      if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
        DataGet = xmlhttp.responseText;
        DataGet = eval(DataGet);
        for (var i = 0; i < DataGet.length; i++) {
          DataGet[i].date = (new Date(DataGet[i].date)).toDateString();
        }
      }
    }
    xmlhttp.open("GET", "http://localhost:8080/getData", false);
    xmlhttp.send();
    if (this.isMounted()) {
      this.setState({
        INFOS: DataGet
      });
    }
  },

  render: function () {
    return (
      <div className="container-fluid">
        <div className="row">
          <div className="col-md-10 col-md-offset-1">
            <NavBar/>
            <Modal onContentSubmit={this.handleContentSubmit}/>
            <PanelList infos={this.state.INFOS}/>
          </div>
        </div>
      </div>
    );
  }
});


/*pand-body组件*/
var PanelBody = React.createClass({
  render: function () {
    return (
      <div className="panel-body">
        {this.props.content}
      </div>
    );
  }
});

/*单个面板组件*/
var Panel = React.createClass({
  render: function () {
    return (
      <div className="col-md-6">
        <div className="panel panel-success">
          <div className="panel-heading">{this.props.heading}</div>
          <PanelBody content={this.props.content}/>
        </div>
      </div>
    );
  }
});

/*面板列表组*/
var PanelList = React.createClass({
  render: function () {
    var panels = [];
    this.props.infos.forEach(function (info) {
      panels.push(<Panel heading={info.date} content={info.content}/>)
    })
    return (
      <div id="panelList" className="row">
        {panels}
      </div>
    );
  }
});

/*导航条主体*/
var NavBar = React.createClass({
  render: function () {
    return (
      <nav className="navbar navbar-default" role="navigation">
        <div className="container-fluid">
          <NavBarHeader/>
          <NavBarCollapse/>
        </div>
      </nav>
    );
  }
});

/*导航条头部*/
var NavBarHeader = React.createClass({
  render: function () {
    return (
      <div className="navbar-header">
        <button type="button" className="navbar-toggle collapsed" data-toggle="collapse"
                data-target="#navbar-collapse">
          <span className="sr-only">Toggle navigation</span>
          <span className="icon-bar"></span>
          <span className="icon-bar"></span>
          <span className="icon-bar"></span>
        </button>
        <a className="navbar-brand" href="#">Summery</a>
      </div>
    );
  }
});

/*导航条内容*/
var NavBarCollapse = React.createClass({
  render: function () {
    return (
      <div className="collapse navbar-collapse" id="navbar-collapse">
        <form className="navbar-form navbar-left" role="search">
          <div className="form-group">
            <input type="text" className="form-control" placeholder="Search"/>
          </div>
          <button type="submit" className="btn btn-default">Submit</button>
        </form>
        <button className="btn btn-default navbar-btn navbar-right" data-toggle="modal" data-target="#mainModal">Add
        </button>
      </div>
    );
  }
});

/*模态框*/
var Modal = React.createClass({
  getInitialState: function () {
    return {content: ""};
  },

  handleTextChange: function (e) {
    this.setState({content: e.target.value});
  },

  //点击submit后的处理函数,其中content来自模态框的state
  handleClick: function (e) {
    this.props.onContentSubmit(this.state.content);
  },

  render: function () {
    return (
      <div className="modal fade" id="mainModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
           aria-hidden="true">
        <div className="modal-dialog">
          <div className="modal-content">

            <div className="modal-header">
              <button type="button" className="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                className="sr-only">Close</span></button>
              <h4 className="modal-title">Today's summary</h4>
            </div>

            <div className="modal-body">
              <form role="form">
                <div className="form-group">
                  <label for="inputContent">Today's summary：</label>
                  <input id="inputContent" type="text" className="form-control" placeholder="Enter today's summary" onChange={this.handleTextChange}/>
                </div>
              </form>
            </div>

            <div className="modal-footer">
              <button type="button" className="btn btn-default" data-dismiss="modal">Close</button>
              <button type="button" className="btn btn-primary" onClick={this.handleClick} data-dismiss="modal">Submit
              </button>
            </div>

          </div>
        </div>
      </div>
    );
  }
});


ReactDOM.render(<App/>, document.getElementById("app"));


const { HashRouter, Route, Switch } = window.ReactRouterDOM;

const App = () => {
  return (
    <HashRouter>
      <Layout>
        <Switch>
          <Route exact path='/' component={Shows} />

          {/* these are needed for jsFiddle to render Shows properly */}
          <Route path='/_display/' component={Shows} />
          <Route path='/shows' component={Shows} />

          <Route path='/upload' component={UploadInventory} />
          <Route path='/order' component={Order} />
          <Route path='*' component={NotFound} />
        </Switch>
      </Layout>
    </HashRouter>
  );
};
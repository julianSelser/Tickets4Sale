// Select the node we wish to mount our React application to
const MOUNT_NODE = document.querySelector('#app');

// Grab components out of the ReactRouterDOM that we will be using
const { HashRouter, Route, Switch, NavLink } = window.ReactRouterDOM;

// PropTypes is used for typechecking
const PropTypes = window.PropTypes;

// Home page component
const Home = () => {
  return <h1>Here we are at the home page.</h1>;
};

// AboutUs page component
const AboutUs = () => {
  return <h1>Now we're here at the about us page.</h1>;
};

// ContactUs page component
const ContactUs = () => {
  // Prevent form submit
  const handleSubmit = (e) => {
    e.preventDefault();
    return false;
  };

  return (
    <div>
      <h1>Contact Us</h1>
      <form onClick={handleSubmit}>
        <input type='text' placeholder='name' />
        <input type='text' placeholder='email' />
        <textarea />
        <button>Submit</button>
      </form>
    </div>
  );
};

// NotFoundPage component
// props.match.url contains the current url route
const NotFoundPage = ({ match }) => {
    const {url} = match;

    return (
    <div>
      <h1>Whoops!</h1>
      <p><strong>{url.replace('/','')}</strong> could not be located.</p>
    </div>
    );
};

// Header component is our page title and navigation menu
const Header = () => {
    // This is just needed to set the Home route to active
  // in jsFiddle based on the URI location. Ignore.
    const checkActive = (match, location) => {
    if(!location) return false;
    const {pathname} = location;

    return pathname.indexOf('/julian') !== -1 || pathname.indexOf('/_display/') !== -1;
  }

  return (
    <header>
      <nav>
        <ul className='navLinks'>
          {/* Your home route path would generally just be '/'' */}
          <li><NavLink to="/julian" isActive={checkActive}>Home</NavLink></li>
          <li><NavLink to="/about">About</NavLink></li>
          <li><NavLink to="/contact">Contact</NavLink></li>
        </ul>
      </nav>
    </header>
  );
};

// Footer component, because I love you.
const Footer = () => {
  return (
    <footer>
      <p>Made with 💪 by Julian</p>
    </footer>
  );
};

// Out layout component which switches content based on the route
const Layout = ({children}) => {
  return (
    <div>
      <Header />
      <main>{children}</main>
      <Footer />
    </div>
  );
};

// Ensure the 'children' prop has a value (required) and the value is an element.
Layout.propTypes = {
  children: PropTypes.element.isRequired,
};

// The top level component is where our routing is taking place.
// We tell the Layout component which component to render based on the current route.
const App = () => {
  return (
    <HashRouter>
      <Layout>
        <Switch>
          <Route exact path='/' component={Home} />

          {/* these are needed for jsFiddle to render Home properly */}
          <Route path='/julian' component={Home} />
          <Route path='/_display/' component={Home} />

          <Route path='/about' component={AboutUs} />
          <Route path='/contact' component={ContactUs} />
          <Route path='*' component={NotFoundPage} />
        </Switch>
      </Layout>
    </HashRouter>
  );
};

// Render the application
ReactDOM.render(
    <App />,
  MOUNT_NODE
);
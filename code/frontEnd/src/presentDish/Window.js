import React from 'react';
import PropTypes from 'prop-types';
import { withStyles } from '@material-ui/core/styles';
import ExpansionPanel from '@material-ui/core/ExpansionPanel';
import ExpansionPanelDetails from '@material-ui/core/ExpansionPanelDetails';
import ExpansionPanelSummary from '@material-ui/core/ExpansionPanelSummary';
import Typography from '@material-ui/core/Typography';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import WindowsFoodList from './WindowsFoodList';

const styles = theme => ({
    root: {
        width: '80%',
      //  maxWidth: '80%',
    },
    heading: {
        fontSize: theme.typography.pxToRem(15),
        flexBasis: '33.33%',
        flexShrink: 0,
    },
    secondaryHeading: {
        fontSize: theme.typography.pxToRem(15),
        color: theme.palette.text.secondary,
    },
});

class Window extends React.Component {
    state = {
        expanded:"panel1",
        commentList:[],
        FoodList:[],
        WindowId:0,

    };

    constructor(props) {
        super(props);
    }

    handleChange = panel => (event, expanded) => {
        this.setState({
            expanded: expanded ? panel : false,
        });
    };

    render() {
        const { classes } = this.props;
        const { expanded } = this.state;

        return (
            <div className={classes.root}>
                <ExpansionPanel expanded={expanded === 'panel1'} onChange={this.handleChange('panel1')}>
                    <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
                        <Typography className={classes.heading}>菜品展示</Typography>
                        <Typography className={classes.secondaryHeading}>今天吃了么</Typography>
                    </ExpansionPanelSummary>
                    <ExpansionPanelDetails>
                        <Typography>
                            <WindowsFoodList dishesList={this.props.dishesList}/>
                        </Typography>
                    </ExpansionPanelDetails>
                </ExpansionPanel>
                <ExpansionPanel expanded={expanded === 'panel2'} onChange={this.handleChange('panel2')}>
                    <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
                        <Typography className={classes.heading}>实时人数</Typography>
                        <Typography className={classes.secondaryHeading}>
                             实时人数
                        </Typography>
                    </ExpansionPanelSummary>
                    <ExpansionPanelDetails>
                        <Typography>
                            <img src="https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=4099675241,2760395260&fm=15&gp=0.jpg"/>
                        </Typography>
                    </ExpansionPanelDetails>
                </ExpansionPanel>
                <ExpansionPanel expanded={expanded === 'panel3'} onChange={this.handleChange('panel3')}>
                    <ExpansionPanelSummary expandIcon={<ExpandMoreIcon />}>
                        <Typography className={classes.heading}>评论</Typography>
                        <Typography className={classes.secondaryHeading}>窗口评论
                        </Typography>
                    </ExpansionPanelSummary>
                    <ExpansionPanelDetails>
                        <Typography>
                           <img src="
                           https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1531286712762&di=48c770515f83f496ccd715b0137c3a40&imgtype=0&src=http%3A%2F%2Fimg.18183.com%2Fuploads%2Fallimg%2F183%2F180104%2F1A9415638-1.jpg
                           "/>
                        </Typography>
                    </ExpansionPanelDetails>
                </ExpansionPanel>
            </div>
        );
    }
}

Window.propTypes = {
    classes: PropTypes.object.isRequired,
};

export default withStyles(styles)(Window);